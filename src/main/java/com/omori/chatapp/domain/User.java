package com.omori.chatapp.domain;

import com.omori.chatapp.domain.enums.UserStatus;
import java.time.LocalDateTime;
import java.util.HashSet;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String userName;
  @Column(nullable = false)
  private String password;
  @Column(unique = true, nullable = false)
  private String email;

  private String fullName;
  private String avatarUrl;

  @Enumerated(EnumType.STRING)
  private UserStatus status;

  private LocalDateTime lastSeen;

  @ElementCollection
  private Set<String> roomIds = new HashSet<>();

  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime updateAt;

  public User(Long id, String userName, String password, String email, String fullName, String avatarUrl,
      UserStatus status, LocalDateTime lastSeen, Set<String> roomIds, LocalDateTime createdAt, LocalDateTime updateAt) {
    this.id = id;
    this.userName = userName;
    this.password = password;
    this.email = email;
    this.fullName = fullName;
    this.avatarUrl = avatarUrl;
    this.status = status;
    this.lastSeen = lastSeen;
    this.roomIds = roomIds != null ? roomIds : new HashSet<>();
    this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    this.updateAt = updateAt;
  }

  // Getters & setters
  public Long getId() {
    return id;
  }

  public String getUsername() {
    return userName;
  }

  public String getFullName() {
    return fullName;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public UserStatus getStatus() {
    return status;
  }

  public LocalDateTime getLastSeen() {
    return lastSeen;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdateAt() {
    return updateAt;
  }

}
