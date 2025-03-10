package com.omori.chatapp.domain;

import com.omori.chatapp.domain.enums.MessageType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "messages")
public class Message {
  @Id
  private String id;

  @Indexed
  private String roomId;

  private String senderId;

  private String content;

  private MessageType type;

  private List<Attachment> attachments = new ArrayList<>();

  private Set<String> readBy = new HashSet<>();

  private Set<Reaction> reactions = new HashSet<>();

  private String replyToId;

  private LocalDateTime sentAt;

  private LocalDateTime updatedAt;

  public static class Attachment {
    private String url;
    private String name;
    private String type;
    private long size;

    // Getters and setters
  }

  public static class Reaction {
    private String emoji;
    private String userId;
    private LocalDateTime createdAt;

    // Getters and setters
  }

  // Constructors, getters, setters, equals, hashCode
}
