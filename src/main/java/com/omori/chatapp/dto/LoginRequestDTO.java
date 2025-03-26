package com.omori.chatapp.dto;

import lombok.Data;

/**
 * LoginRequestDTO
 */
@Data
public class LoginRequestDTO {

  private String username;
  private String password;

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }
}
