package com.omori.chatapp.dto;

import com.omori.chatapp.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
  private Long id;
  private String username;
  private String email;

  public UserDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
  }
}
