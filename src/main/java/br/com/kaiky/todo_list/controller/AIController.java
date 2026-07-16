package br.com.kaiky.todo_list.controller;

import br.com.kaiky.todo_list.dto.AiChatRequestDTO;
import br.com.kaiky.todo_list.dto.AiChatResponseDTO;
import br.com.kaiky.todo_list.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AIController {
    private final AiService aiService;

    public AIController(AiService aiService) {
        this.aiService = aiService;
    }

    @Operation(
            summary = "Chat with the task management assistant",
            description = """
        Processes natural-language requests and may use AI tools
        to retrieve, create, update, or delete tasks.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Request processed successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request"
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "AI provider error"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected internal error"
            )
    })
    @PostMapping("/chat")
    public ResponseEntity<AiChatResponseDTO> chat(
        @Valid @RequestBody AiChatRequestDTO dto
    ) {
        return ResponseEntity.ok(aiService.chat(dto));
    }
}
