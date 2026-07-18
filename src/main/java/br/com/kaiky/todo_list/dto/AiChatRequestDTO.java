package br.com.kaiky.todo_list.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record AiChatRequestDTO(
    UUID conversationId,
    @NotBlank(message = "message required")
    String message
) {}
