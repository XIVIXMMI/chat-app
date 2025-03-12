package com.omori.chatapp.dto;

import com.omori.chatapp.domain.enums.RoomPrivacy;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RoomDTO {
  private String id;
  private String name;
  private RoomPrivacy type;
  private String createdBy;
  private List<UserDTO> participants;
  private LocalDateTime createdAt;

  // Constructor không tham số
  public RoomDTO() {
  }

  // Constructor đầy đủ tham số
  public RoomDTO(String id, String name, RoomPrivacy type, String createdBy, List<UserDTO> participants,
      LocalDateTime createdAt) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.createdBy = createdBy;
    this.participants = participants;
    this.createdAt = createdAt;
  }

  // Getters và Setters
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

  public List<UserDTO> getParticipants() {
    return participants;
  }

  public void setParticipants(List<UserDTO> participants) {
    this.participants = participants;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
