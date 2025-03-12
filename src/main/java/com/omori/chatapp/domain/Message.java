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

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
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

  public Message(String id, String roomId, String senderId, String content, MessageType type,
      List<Attachment> attachments, Set<String> readBy, Set<Reaction> reactions,
      String replyToId, LocalDateTime sentAt, LocalDateTime updatedAt) {
    this.id = id;
    this.roomId = roomId;
    this.senderId = senderId;
    this.content = content;
    this.type = type;
    this.attachments = attachments != null ? attachments : new ArrayList<>();
    this.readBy = readBy != null ? readBy : new HashSet<>();
    this.reactions = reactions != null ? reactions : new HashSet<>();
    this.replyToId = replyToId;
    this.sentAt = sentAt;
    this.updatedAt = updatedAt;
  }

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

  public String getSenderId() {
    return senderId;
  }

  public void setSenderId(String senderId) {
    this.senderId = senderId;
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

  public List<Attachment> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<Attachment> attachments) {
    this.attachments = attachments;
  }

  public Set<String> getReadBy() {
    return readBy;
  }

  public void setReadBy(Set<String> readBy) {
    this.readBy = readBy;
  }

  public Set<Reaction> getReactions() {
    return reactions;
  }

  public void setReactions(Set<Reaction> reactions) {
    this.reactions = reactions;
  }

  public String getReplyToId() {
    return replyToId;
  }

  public void setReplyToId(String replyToId) {
    this.replyToId = replyToId;
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

  public static class Attachment {
    private String url;
    private String name;
    private String type;
    private long size;

    // Getters and Setters for Attachment
    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public long getSize() {
      return size;
    }

    public void setSize(long size) {
      this.size = size;
    }

  }

  public static class Reaction {
    private String emoji;
    private String userId;
    private LocalDateTime createdAt;

    // Getters and Setters for Reaction
    public String getEmoji() {
      return emoji;
    }

    public void setEmoji(String emoji) {
      this.emoji = emoji;
    }

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
    }
  }

}
