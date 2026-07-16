package br.com.kaiky.todo_list.service;

import br.com.kaiky.todo_list.prompt.PromptInjectionAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import br.com.kaiky.todo_list.dto.AiChatRequestDTO;
import br.com.kaiky.todo_list.dto.AiChatResponseDTO;
import br.com.kaiky.todo_list.prompt.PromptService;
import br.com.kaiky.todo_list.tools.TaskTools;

@Service
public class AiService {

    private final ChatClient chatClient;
    private final TaskTools taskTools;
    private final PromptService promptService;

    public AiService(
            ChatClient.Builder chatClientBuilder,
            TaskTools taskTools,
            PromptService promptService,
            PromptInjectionAdvisor promptInjectionAdvisor
    ) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(promptInjectionAdvisor)
                .build();

        this.taskTools = taskTools;
        this.promptService = promptService;

    }

    public AiChatResponseDTO chat(AiChatRequestDTO dto) {
        String response = chatClient
                .prompt()
                .system(promptService.getSystemPrompt())
                .user(dto.message())
                .tools(taskTools)
                .call()
                .content();

        return new AiChatResponseDTO(response);
    }
}