package com.omori.chatapp.service;

import com.omori.chatapp.dto.user.UserProfileResponseDTO;
import com.omori.chatapp.dto.user.UserProfileUpdateDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.omori.chatapp.entity.User;
import com.omori.chatapp.dto.user.PasswordChangeRequestDTO;

public interface UserService {

  Page<User> findAllUsers(Pageable pageable);

  User findUserById(Long id);

  void softDeleteUserById(Long id);

  Page<User> findDeletedUsers(Pageable pageable);

  User updateUserProfile(Long id, UserProfileUpdateDTO userProfileUpdateDTO);

  void changePassword(Long id, PasswordChangeRequestDTO request);

}
