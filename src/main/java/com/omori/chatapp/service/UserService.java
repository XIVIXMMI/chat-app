package com.omori.chatapp.service;

import java.util.List;

import com.omori.chatapp.domain.User;

public interface UserService {

  List<User> findAllUsers();

  User findUserById(Long id);

  void deleteUserById(Long id);

}
