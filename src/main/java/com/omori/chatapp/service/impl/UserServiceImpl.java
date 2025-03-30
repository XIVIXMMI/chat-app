package com.omori.chatapp.service.impl;

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
    return userRepository.findAll();
  }

  @Override
  public User findUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
  }

  @Override
  public void deleteUserById(Long id) {
    if (!userRepository.existsById(id)) {
      throw new UserNotFoundException("User not found with id: " + id);
    }
    userRepository.deleteById(id);
  }
}
