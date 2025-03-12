package com.omori.chatapp.dto;

import com.omori.chatapp.domain.enums.MessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.omori.chatapp.domain.Message;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO {
  private String id;
  private String roomId;
  private UserDTO sender;
  private String content;
  private MessageType type;
  private List<Message.Attachment> attachments;
  private List<String> readBy;
  private List<Message.Reaction> reactions;
  private String replyToId;
  private MessageDTO replyTo;
  private LocalDateTime sentAt;
  private LocalDateTime updatedAt;

  // Getters and Setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRoomId() {
    return roomId;
  }

  public void setRoomId(String roomId) {
    this.roomId = roomId;
  }

  public UserDTO getSender() {
    return sender;
  }

  public void setSender(UserDTO sender) {
    this.sender = sender;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public MessageType getType() {
    return type;
  }

  public void setType(MessageType type) {
    this.type = type;
  }

  public List<Message.Attachment> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<Message.Attachment> attachments) {
    this.attachments = attachments;
  }

  public List<String> getReadBy() {
    return readBy;
  }

  public void setReadBy(List<String> readBy) {
    this.readBy = readBy;
  }

  public List<Message.Reaction> getReactions() {
    return reactions;
  }

  public void setReactions(List<Message.Reaction> reactions) {
    this.reactions = reactions;
  }

  public String getReplyToId() {
    return replyToId;
  }

  public void setReplyToId(String replyToId) {
    this.replyToId = replyToId;
  }

  public MessageDTO getReplyTo() {
    return replyTo;
  }

  public void setReplyTo(MessageDTO replyTo) {
    this.replyTo = replyTo;
  }

  public LocalDateTime getSentAt() {
    return sentAt;
  }

  public void setSentAt(LocalDateTime sentAt) {
    this.sentAt = sentAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}
