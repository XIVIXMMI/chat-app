package com.omori.chatapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
// @AllArgsConstructor
public class AuthResponseDTO {
  private String token;

  public AuthResponseDTO(String token) {
    this.token = token;
  }
}
