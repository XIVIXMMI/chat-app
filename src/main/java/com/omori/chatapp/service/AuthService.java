package com.omori.chatapp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omori.chatapp.domain.User;
import com.omori.chatapp.domain.enums.UserEnum.AuthProvider;
import com.omori.chatapp.domain.enums.UserEnum.Role;
import com.omori.chatapp.dto.RegisterRequestDTO;
import com.omori.chatapp.repository.UserRepository;

/**
 * AuthService
 */
@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public String registerUser(RegisterRequestDTO request) {
    // check if email or username has been existed
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new IllegalArgumentException("Username already existed!");
    }
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new IllegalArgumentException("Email already existed!");
    }

    String hashedPassword = passwordEncoder.encode(request.getPassword());

    // create new user
    User user = new User();
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setPasswordHash(hashedPassword);
    user.setRole(Role.USER);
    user.setAuthProvider(AuthProvider.LOCAL);

    userRepository.save(user);

    return "Register successfully!!";
  }
}
