package com.omori.chatapp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.omori.chatapp.domain.User;
import com.omori.chatapp.repository.UserRepository;

@Service
public class UserDetailsSeviceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public UserDetailsSeviceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User is not exist"));
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPasswordHash(),
        new ArrayList<>());
  }
}
