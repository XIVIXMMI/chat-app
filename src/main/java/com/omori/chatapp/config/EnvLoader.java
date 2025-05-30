package com.omori.chatapp.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvLoader {

  public final Dotenv dotenv = Dotenv.load();

  public String get(String key) {
    return dotenv.get(key);
  }

  @PostConstruct
  public void loadEnvToSystemProperties() {
    // Server
    System.setProperty("server.port", dotenv.get("SERVER_PORT"));

    // JWT
    System.setProperty("jwt.secret", dotenv.get("JWT_SECRET"));
    System.setProperty("jwt.expiration-time", dotenv.get("JWT_EXPIRATION"));

    // MySQL
    System.setProperty("spring.datasource.url", dotenv.get("MYSQL_URL"));
    System.setProperty("spring.datasource.username", dotenv.get("MYSQL_USERNAME"));
    System.setProperty("spring.datasource.password", dotenv.get("MYSQL_PASSWRD"));

    // MongoDB
    System.setProperty("spring.data.mongodb.uri", dotenv.get("MONGODB_URL"));

    // RabbitMQ
    System.setProperty("spring.rabbitmq.host", dotenv.get("RABBITMQ_HOST"));
    System.setProperty("spring.rabbitmq.port", dotenv.get("RABBITMQ_PORT"));
  }
}
