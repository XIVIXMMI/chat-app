package com.omori.chatapp.domain;

import com.omori.chatapp.domain.enums.RoomPrivacy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "rooms")
public class Room {
  @Id
  private String id;

  private String name;

  private RoomPrivacy type;

  private String createdBy;

  private Set<String> participantIds = new HashSet<>();

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  // Constructors, getters, setters, equals, hashCode
}
