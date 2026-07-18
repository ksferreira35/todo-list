package br.com.kaiky.todo_list.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.kaiky.todo_list.entity.ChatConversation;

public interface ChatConversationRepository extends JpaRepository<ChatConversation, UUID> {
}
