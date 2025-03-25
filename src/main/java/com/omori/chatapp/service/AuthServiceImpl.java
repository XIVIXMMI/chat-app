package com.omori.chatapp.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.omori.chatapp.domain.User;
import com.omori.chatapp.domain.enums.UserEnum.AuthProvider;
import com.omori.chatapp.domain.enums.UserEnum.Role;
import com.omori.chatapp.dto.RegisterRequestDTO;
import com.omori.chatapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
// @RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  @Override
  public void registerUser(RegisterRequestDTO request) {
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

  }
}
