package com.omori.chatapp.service.impl;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omori.chatapp.entity.enums.UserEnum.Status;
import com.omori.chatapp.exception.UserNotFoundException;
import com.omori.chatapp.repository.UserRepository;
import com.omori.chatapp.service.UserStatusService;

@Service
public class UserStatusServiceImpl implements UserStatusService {
  private final UserRepository userRepository;

  public UserStatusServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public void updateLastActivity(String username) {
    userRepository.findByUsernameAndDeletedAtIsNull(username)
        .ifPresentOrElse(user -> {
          user.setLastActivity(LocalDateTime.now());
          userRepository.save(user);
        }, () -> {
          throw new UserNotFoundException("User not found: " + username);
        });
  }

  @Override
  @Transactional
  public void markOnline(String username) {
    userRepository.findByUsernameAndDeletedAtIsNull(username)
        .ifPresentOrElse(user -> {
          user.setStatus(Status.ONLINE);
          user.setLastActivity(LocalDateTime.now());
          userRepository.save(user);
        }, () -> {
          throw new UserNotFoundException("User not found: " + username);
        });
  }

  @Override
  @Transactional
  public void markOffline(String username) {
    userRepository.findByUsernameAndDeletedAtIsNull(username)
        .ifPresentOrElse(user -> {
          user.setStatus(Status.OFFLINE);
          user.setLastActivity(LocalDateTime.now());
          userRepository.save(user);
        }, () -> {
          throw new UserNotFoundException("User not found: " + username);
        });
  }

  @Override
  @Transactional
  @Scheduled(fixedRate = 60000)
  public void markUserIdle() {
    LocalDateTime minutesAgo = LocalDateTime.now().minusMinutes(5);
    userRepository.findAllByStatusAndLastActivityBefore(Status.ONLINE, minutesAgo)
        .forEach(user -> {
          user.setStatus(Status.AWAY);
          userRepository.save(user);
        });
  }

  @Override
  @Transactional
  public void updateUserStatus(String username, Status status) {
    userRepository.findByUsernameAndDeletedAtIsNull(username)
        .ifPresentOrElse(user -> {
          user.setStatus(status);
          user.setLastActivity(LocalDateTime.now());
          userRepository.save(user);
        }, () -> {
          throw new UserNotFoundException("User not found: " + username);
        });
  }

}
