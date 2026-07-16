package br.com.kaiky.todo_list.dto;

import jakarta.validation.constraints.NotBlank;

public record AiChatRequestDTO(
    @NotBlank(message = "message required")
    String message
) {}
