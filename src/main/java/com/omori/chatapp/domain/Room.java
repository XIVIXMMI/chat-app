package com.omori.chatapp.domain;

import com.omori.chatapp.domain.enums.RoomPrivacy;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Document(collection = "rooms")
public class Room {
  @Id
  private String id;

  private String name;

  @Enumerated(EnumType.STRING)
  private RoomPrivacy type;

  private String createdBy;

  private Set<String> participantIds = new HashSet<>();

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  // Constructors
  public Room() {
  }

  public Room(String id, String name, RoomPrivacy type, String createdBy, Set<String> participantIds,
      LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.createdBy = createdBy;
    this.participantIds = participantIds;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // Getters and Setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RoomPrivacy getType() {
    return type;
  }

  public void setType(RoomPrivacy type) {
    this.type = type;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Set<String> getParticipantIds() {
    return participantIds;
  }

  public void setParticipantIds(Set<String> participantIds) {
    this.participantIds = participantIds;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}
