package com.omori.chatapp.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.omori.chatapp.exception.UserNotFoundException;
import com.omori.chatapp.repository.UserRepository;
import com.omori.chatapp.domain.User;
import com.omori.chatapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> findAllUsers() {
    return userRepository.findAllActiveUsers();
  }

  @Override
  public User findUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
  }

  @Override
  public List<User> findDeletedUsers() {
    return userRepository.findAllDeletedUsers(); // Get list of deleted users
  }

  @Override
  public void softDeleteUserById(Long id) {
    User user = userRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    user.setDeletedAt(LocalDateTime.now()); // Mark as deleted
    userRepository.save(user); // update Database
  }
}
