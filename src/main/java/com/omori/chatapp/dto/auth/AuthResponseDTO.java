package com.omori.chatapp.dto.auth;

import lombok.Data;

@Data
// @AllArgsConstructor
public class AuthResponseDTO {
  private String token;

  public AuthResponseDTO(String token) {
    this.token = token;
  }

  public String getToken() {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
