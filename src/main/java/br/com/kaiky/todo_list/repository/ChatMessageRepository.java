package br.com.kaiky.todo_list.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.kaiky.todo_list.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    List<ChatMessage> findByConversation_IdOrderByCreatedAtAsc(UUID conversationId);
}
