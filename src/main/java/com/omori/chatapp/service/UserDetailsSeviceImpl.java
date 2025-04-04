package com.omori.chatapp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.omori.chatapp.domain.User;
import com.omori.chatapp.repository.UserRepository;
import com.omori.chatapp.security.CustomUserDetails;

@Service
public class UserDetailsSeviceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailsSeviceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User is not exist"));
    return new CustomUserDetails(user);
  }
}
