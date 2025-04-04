package com.omori.chatapp.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.omori.chatapp.domain.User;
import com.omori.chatapp.dto.UserUpdateDTO;

public interface UserService {

  Page<User> findAllUsers(Pageable pageable);

  User findUserById(Long id);

  void softDeleteUserById(Long id);

  Page<User> findDeletedUsers(Pageable pageable);

  User updateUserProfile(Long id, UserUpdateDTO userUpdateDTO);

}
