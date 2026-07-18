package br.com.kaiky.todo_list.repository;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisChatMemoryRepository implements ChatMemoryRepository {

    private static final String KEY_PREFIX = "chat:memory:";
    private static final TypeReference<List<StoredMessage>> STORED_MESSAGES =
            new TypeReference<>() {
            };

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    private final Duration ttl;

    public RedisChatMemoryRepository(
            StringRedisTemplate redisTemplate,
            @Value("${app.chat.memory.redis-ttl-days:7}") long ttlDays
    ) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
        this.ttl = Duration.ofDays(ttlDays);
    }

    @Override
    public List<String> findConversationIds() {
        Set<String> keys = redisTemplate.keys(KEY_PREFIX + "*");

        if (keys == null || keys.isEmpty()) {
            return List.of();
        }

        return keys.stream()
                .map(key -> key.substring(KEY_PREFIX.length()))
                .toList();
    }

    @Override
    public List<Message> findByConversationId(String conversationId) {
        String json = redisTemplate.opsForValue().get(key(conversationId));

        if (json == null || json.isBlank()) {
            return List.of();
        }

        try {
            return objectMapper.readValue(json, STORED_MESSAGES)
                    .stream()
                    .map(RedisChatMemoryRepository::toMessage)
                    .toList();
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException(
                    "Could not read chat memory from Redis.",
                    exception
            );
        }
    }

    @Override
    public void saveAll(String conversationId, List<Message> messages) {
        List<StoredMessage> storedMessages = messages.stream()
                .filter(RedisChatMemoryRepository::isSupportedMessageType)
                .map(StoredMessage::from)
                .toList();

        try {
            redisTemplate.opsForValue().set(
                    key(conversationId),
                    objectMapper.writeValueAsString(storedMessages),
                    ttl
            );
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException(
                    "Could not save chat memory to Redis.",
                    exception
            );
        }
    }

    @Override
    public void deleteByConversationId(String conversationId) {
        redisTemplate.delete(key(conversationId));
    }

    private static String key(String conversationId) {
        return KEY_PREFIX + conversationId;
    }

    private static boolean isSupportedMessageType(Message message) {
        return message.getMessageType() == MessageType.USER
                || message.getMessageType() == MessageType.ASSISTANT
                || message.getMessageType() == MessageType.SYSTEM;
    }

    private static Message toMessage(StoredMessage storedMessage) {
        return switch (storedMessage.type()) {
            case USER -> new UserMessage(storedMessage.text());
            case ASSISTANT -> new AssistantMessage(storedMessage.text());
            case SYSTEM -> new SystemMessage(storedMessage.text());
            default -> throw new IllegalArgumentException(
                    "Unsupported chat memory message type: " + storedMessage.type()
            );
        };
    }

    private record StoredMessage(MessageType type, String text) {
        static StoredMessage from(Message message) {
            return new StoredMessage(message.getMessageType(), message.getText());
        }
    }
}
