package com.code.blog.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI blogOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Blog APIs")
                        .description("Spring Boot API Documentation for Blog APIs")
                        .version("1")
                        .contact(new Contact().name("Donny J Mathew").url("www.linkedin.com/in/donny-j-mathew")
                                .email("donny.j.mathew@gmail.com"))
                        .license(new License().name("License Of API").url("API License URL")))
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new Components().addSecuritySchemes("JWT", createSecurityScheme()));
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}
