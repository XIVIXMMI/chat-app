package com.omori.chatapp.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import jakarta.persistence.*;
import java.util.Set;

import lombok.Data;

@Entity
@Data
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

  public enum UserStatus {
    ONLINE,
    OFFLINE,
    AWAY,
    BUSY
  }

}
