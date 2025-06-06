package com.omori.chatapp.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("message")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatMessage {
  @Id
  private String id;
  private String conversationId;
  private String sender;
  private String receiver;
  private String content;
  private LocalDateTime timestamp;
  private String type;
}
