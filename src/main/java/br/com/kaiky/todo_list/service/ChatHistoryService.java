package br.com.kaiky.todo_list.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.kaiky.todo_list.entity.ChatConversation;
import br.com.kaiky.todo_list.entity.ChatMessage;
import br.com.kaiky.todo_list.repository.ChatConversationRepository;
import br.com.kaiky.todo_list.repository.ChatMessageRepository;

@Service
public class ChatHistoryService {

    private static final int TITLE_MAX_LENGTH = 80;

    private final ChatConversationRepository chatConversationRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatHistoryService(
            ChatConversationRepository chatConversationRepository,
            ChatMessageRepository chatMessageRepository
    ) {
        this.chatConversationRepository = chatConversationRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    @Transactional
    public ChatConversation getOrCreateConversation(
            UUID conversationId,
            String firstMessage
    ) {
        return chatConversationRepository.findById(conversationId)
                .orElseGet(() -> chatConversationRepository.save(
                        new ChatConversation(conversationId, buildTitle(firstMessage))
                ));
    }

    @Transactional
    public void saveUserMessage(UUID conversationId, String content) {
        saveMessage(conversationId, ChatMessage.Role.USER, content);
    }

    @Transactional
    public void saveAssistantMessage(UUID conversationId, String content) {
        saveMessage(conversationId, ChatMessage.Role.ASSISTANT, content);
    }

    private void saveMessage(
            UUID conversationId,
            ChatMessage.Role role,
            String content
    ) {
        ChatConversation conversation = chatConversationRepository
                .findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Conversation not found: " + conversationId
                ));

        chatMessageRepository.save(new ChatMessage(conversation, role, content));
    }

    private static String buildTitle(String message) {
        String normalizedMessage = message == null ? "" : message.trim();

        if (normalizedMessage.isBlank()) {
            return "New conversation";
        }

        if (normalizedMessage.length() <= TITLE_MAX_LENGTH) {
            return normalizedMessage;
        }

        return normalizedMessage.substring(0, TITLE_MAX_LENGTH - 3) + "...";
    }
}
