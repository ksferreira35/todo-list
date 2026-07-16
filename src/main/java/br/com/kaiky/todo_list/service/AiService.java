package br.com.kaiky.todo_list.service;

import br.com.kaiky.todo_list.dto.AiChatResponseDTO;
import br.com.kaiky.todo_list.tools.TaskTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import br.com.kaiky.todo_list.dto.AiChatRequestDTO;

@Service
public class AiService {
    private final ChatClient chatClient;
    private final TaskTools taskTools;

    public AiService(
            ChatClient.Builder chatClientBuilder,
            TaskTools taskTools
    ) {
        this.chatClient = chatClientBuilder.build();
        this.taskTools = taskTools;
    }

    public AiChatResponseDTO chat(AiChatRequestDTO dto) {
        String response = chatClient
                            .prompt()
                            .system("""
                                Your name is Liyz.
                            
                                You are a task management assistant.
                            
                                Answer normal conversational questions directly without calling tools.
                            
                                Only call a tool when the user explicitly asks to:
                                - list tasks
                                - retrieve a specific task
                                - create a task
                                - update a task completion status
                                - delete a task
                            
                                Do not call tools for:
                                - greetings
                                - questions about your name
                                - general conversation
                                - questions about your capabilities
                                - requests unrelated to task management
                            
                                Never claim that an operation succeeded unless the corresponding
                                tool was executed successfully.
                            
                                Ask for any missing required information before calling a tool.
                            
                                Never invent task IDs.
                                Never use placeholder task IDs such as "unknown", "null",
                                "missing", or "not provided".
                            
                                Only retrieve, update, or delete a specific task when the user
                                provides a valid task UUID.
                            
                                For destructive operations, require a clear and explicit request
                                from the user.
                                """)
                            .user(dto.message())
                            .tools(taskTools)
                            .call()
                            .content();

        return new AiChatResponseDTO(response);
    }
}
