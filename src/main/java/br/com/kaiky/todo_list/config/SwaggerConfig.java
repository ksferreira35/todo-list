package br.com.kaiky.todo_list.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                .title("Todo List API")
                .version("v1.0.0")
                .description("REST API for task management built with Spring Boot.")
                .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT")
                )
                .contact(new Contact()
                    .name("Kaiky Ferreira")
                    .url("https://github.com/ksferreira35")
                ));
    }
}
