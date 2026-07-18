package br.com.kaiky.todo_list.dto;

import java.util.UUID;

public record AiChatResponseDTO(
    UUID conversationId,
    String response
) {}
