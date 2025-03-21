package com.omori.chatapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * RegisterRequestDTO
 */
@Data
public class RegisterRequestDTO {

  @NotBlank
  @Size(min = 3, max = 50)
  private String username;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Size(min = 6, max = 100)
  private String password;

  private String fullName = "Anonymous"; // default value

  public RegisterRequestDTO() {
  }

  public RegisterRequestDTO(String username, String email, String password, String fullName) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.fullName = fullName;
  }

  /**
   * Getter & Getter
   */

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
