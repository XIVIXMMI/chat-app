package com.omori.chatapp.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;

@Document("message")
public class ChatMessage {
  @Id
  private String id;
  private String conversationId;
  private String sender;
  private String content;
  private LocalDateTime timestamp;
  private String type;
}
