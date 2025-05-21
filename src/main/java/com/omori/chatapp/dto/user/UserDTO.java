package com.omori.chatapp.dto.user;

import com.omori.chatapp.entity.User;

import lombok.Getter;
import lombok.Setter;

/**
 * RESGISTER DTO
 *
 */
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
