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

  @NotBlank(message = "username is not blank")
  @Size(min = 3, max = 50, message = "username must atleast 3 character")
  private String username;

  @NotBlank(message = "email is not blank")
  @Email(message = "Email is not valid")
  private String email;

  @NotBlank(message = "password is not blank")
  @Size(min = 6, max = 100, message = "password is atleast 6 character")
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
