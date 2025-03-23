package com.omori.chatapp.controller;

import java.util.List;
import com.omori.chatapp.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omori.chatapp.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/users")
  public List<User> getAllUser() {
    return userRepository.findAll();
  }

}
