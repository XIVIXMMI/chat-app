package com.omori.chatapp.domain;

import com.omori.chatapp.domain.enums.UserEnum.*;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 50)
  private String username;

  @Column(name = "password_hash", nullable = false, length = 60)
  private String passwordHash;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(name = "avatar_path", length = 200)
  private String avatarPath;

  @Column(name = "phone_number", unique = true, length = 20)
  private String phoneNumber;

  @Column(name = "full_name", length = 250)
  private String fullName = "Anonymous";

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "ENUM('ONLINE','OFFLINE','BUSY','AWAY')")
  private Status status = Status.OFFLINE;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "last_login")
  private LocalDateTime lastLogin;

  @Column(name = "last_activitiy")
  private LocalDateTime lastActivity;

  @Column(name = "is_verified")
  private boolean isVerified = false;

  @Column(name = "email_verified_at")
  private LocalDateTime emailVerifiedAt;

  @Column(name = "login_attempts", nullable = false)
  private int loginAttempt = 0;

  @Column(name = "failed_login_at")
  private LocalDateTime failedLoginAt;

  @Column(name = "is_locked", nullable = false)
  private boolean isLocked = false;

  @Column(name = "two_factor_enabled", nullable = false)
  private boolean twoFactorEnabled = false;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "ENUM('USER','ADMIN','MODERATOR')")
  private Role role = Role.USER;

  @Column(name = "timezone", length = 50)
  private String timeZone = "UTC";

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(name = "session_id", length = 255)
  private String sessionId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "ENUM('LOCAL','GOOGLE','FACEBOOK','GITHUB')")
  private AuthProvider authProvider = AuthProvider.LOCAL;

  @Column(name = "auth_provider_id", length = 255)
  private String authProviderId;

  @Column(columnDefinition = "JSON")
  private String settings;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  public boolean isDeleted() {
    return deletedAt != null;
  }

  public void restore() {
    this.deletedAt = null;
  }

  /*
   * Getter &Setter
   */

  public Long getId() {
    return this.id;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPasswordHash() {
    return this.passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFullName() {
    return this.fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getAvatarPath() {
    return this.avatarPath;
  }

  public void setAvatarPath(String avatarPath) {
    this.avatarPath = avatarPath;
  }

  public Role getRole() {
    return this.role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public void setAuthProvider(AuthProvider authProvider) {
    this.authProvider = authProvider;
  }

  public void setDeletedAt(LocalDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }

  public String getTimeZone() {
    return this.timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }
}
