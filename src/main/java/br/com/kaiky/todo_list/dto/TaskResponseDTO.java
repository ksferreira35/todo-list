package br.com.kaiky.todo_list.dto;

import java.util.UUID;

public record TaskResponseDTO(
    UUID id,
    String title,
    String description,
    boolean completed
) {
    
}
