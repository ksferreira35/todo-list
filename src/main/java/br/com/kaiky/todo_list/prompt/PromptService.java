package br.com.kaiky.todo_list.prompt;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromptService {

    private final PromptLoader promptLoader;

    @Getter
    private final String systemPrompt;

    private static final List<String> PROMPTS = List.of(
            "identity.md",
            "behavior.md",
            "tools.md",
            "formatting.md",
            "facts.md",
            "security.md"
    );

    public PromptService(PromptLoader promptLoader) {
        this.promptLoader = promptLoader;
        this.systemPrompt = buildSystemPrompt();
    }

        private String buildSystemPrompt() {
            return PROMPTS.stream()
                    .map(promptLoader::load)
                    .collect(Collectors.joining("\n\n---\n\n"
                    ));
    }
}