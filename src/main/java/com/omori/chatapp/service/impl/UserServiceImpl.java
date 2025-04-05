package com.omori.chatapp.service.impl;

import java.beans.Transient;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.omori.chatapp.exception.ConflictException;
import com.omori.chatapp.exception.UserNotFoundException;
import com.omori.chatapp.repository.UserRepository;
import com.omori.chatapp.domain.User;
import com.omori.chatapp.dto.UserUpdateDTO;
import com.omori.chatapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Page<User> findAllUsers(Pageable pageable) {
    return userRepository.findAllActiveUsers(pageable);
  }

  @Override
  public User findUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
  }

  @Override
  public Page<User> findDeletedUsers(Pageable pageable) {
    return userRepository.findAllDeletedUsers(pageable); // Get list of deleted users
  }

  @Override
  public void softDeleteUserById(Long id) {
    User user = userRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    user.setDeletedAt(LocalDateTime.now()); // Mark as deleted
    userRepository.save(user); // update Database
  }

  @Override
  @Transactional
  public User updateUserProfile(Long id, UserUpdateDTO updateDTO) {
    User user = userRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    // Update only non-null fields
    if (updateDTO.fullName() != null)
      user.setFullName(updateDTO.fullName());
    if (updateDTO.email() != null) {
      if (userRepository.existsByEmailAndIdNot(updateDTO.email(), id)) {
        throw new ConflictException("Email already in use");
      }
      user.setEmail(updateDTO.email());
    }
    if (updateDTO.phoneNumber() != null) {
      if (userRepository.existsByPhoneNumberAndIdNot(updateDTO.phoneNumber(), id)) {
        throw new ConflictException("Phone number already in use");
      }
      user.setPhoneNumber(updateDTO.phoneNumber());
      ;
    }
    if (updateDTO.avatarPath() != null)
      user.setAvatarPath(updateDTO.avatarPath());
    if (updateDTO.timeZone() != null)
      user.setTimeZone(updateDTO.timeZone());

    return userRepository.save(user);
  }

  private boolean existsByEmailAndIdNot(String email, Long id) {
    return userRepository.existsByEmailAndIdNot(email, id);
  }
}
