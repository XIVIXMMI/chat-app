package com.omori.chatapp.dto;

import com.omori.chatapp.domain.enums.UserStatus;

import com.omori.chatapp.domain.User;
import java.time.LocalDateTime;

public class UserDTO {
  private Long id;
  private String userName;
  private String fullName;
  private String avatarUrl;
  private UserStatus status;
  private LocalDateTime lastSeen;

  public UserDTO(User user) {
    this.id = user.getId();
    this.userName = user.getUsername();
    this.fullName = user.getFullName();
    this.avatarUrl = user.getAvatarUrl();
    this.status = user.getStatus();
    this.lastSeen = user.getLastSeen();
  }

  public static UserDTO fromUser(User user) {
    if (user == null)
      return null;
    return new UserDTO(user);
  }

  // Getters and setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public UserStatus getStatus() {
    return status;
  }

  public void setStatus(UserStatus status) {
    this.status = status;
  }

  public LocalDateTime getLastSeen() {
    return lastSeen;
  }

  public void setLastSeen(LocalDateTime lastSeen) {
    this.lastSeen = lastSeen;
  }
}
