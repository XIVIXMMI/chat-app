package com.omori.chatapp.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.info.Contact;

import org.springframework.context.annotation.*;

import java.util.List;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenApi() {
    return new OpenAPI()
        .info(new Info()
            .title("Chat Application API")
            .version("1.0.0")
            .description("RESTful API for real-time chat Application build with Springboot.")
            .contact(new Contact()
                .name("Khoi Nguyen")
                .email("nguyen.le.programmer@gmail.com")
                .url("https://github.com/XIVIXMMI")))
        .servers(List.of(
            new Server().url("http://localhost:8080").description("Local Dev Server")))
        .components(new Components()
            .addSecuritySchemes("bearer-jwt",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")))
        .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
  }
}
