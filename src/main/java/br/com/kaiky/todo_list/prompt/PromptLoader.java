package br.com.kaiky.todo_list.prompt;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class PromptLoader {

    private static final String PROMPTS_DIRECTORY = "prompts/";

    public String load(String fileName) {
        ClassPathResource resource = new ClassPathResource(
                PROMPTS_DIRECTORY + fileName
        );

        try (var inputStream = resource.getInputStream()) {
            return new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );
        } catch (IOException exception) {
            throw new UncheckedIOException(
                    "Could not load prompt file: " + fileName,
                    exception
            );
        }
    }
}