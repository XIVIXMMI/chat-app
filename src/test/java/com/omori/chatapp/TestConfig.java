package com.omori.chatapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.omori.chatapp.config.EnvLoader;

import jakarta.annotation.PostConstruct;

@Configuration
public class TestConfig {
  @Bean
  @Primary
  public EnvLoader testEnvLoader() {
    return new TestEnvLoader();
  }

  static class TestEnvLoader extends EnvLoader {
    @Override
    public String get(String key) {
      // Return dummy values for tests
      return switch (key) {
        case "SERVER_PORT" -> "8080";
        case "JWT_SECRET" -> "test-secret";
        case "JWT_EXPIRATION" -> "3600000";
        case "MYSQL_URL" -> "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        case "MYSQL_USERNAME" -> "sa";
        case "MYSQL_PASSWRD" -> "";
        case "MONGODB_URL" -> "mongodb://localhost:27017/testdb";
        case "RABBITMQ_HOST" -> "localhost";
        case "RABBITMQ_PORT" -> "5672";
        default -> null;
      };
    }

    @Override
    @PostConstruct
    public void loadEnvToSystemProperties() {
      // Skip setting system properties in tests
    }
  }
}
