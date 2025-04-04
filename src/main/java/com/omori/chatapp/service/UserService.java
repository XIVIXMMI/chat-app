package com.omori.chatapp.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.omori.chatapp.domain.User;

public interface UserService {

  Page<User> findAllUsers(Pageable pageable);

  User findUserById(Long id);

  void softDeleteUserById(Long id);

  Page<User> findDeletedUsers(Pageable pageable);

}
