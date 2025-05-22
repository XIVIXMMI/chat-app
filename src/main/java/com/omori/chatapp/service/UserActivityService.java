package com.omori.chatapp.service;

import com.omori.chatapp.entity.enums.UserEnum.Status;

/**
 * UserActivityService
 */
public interface UserActivityService {

  void updateLastActivity(String username);

  void markOnline(String username);

  void markOffline(String username);

  void markUserIdle();

  void updateUserStatus(String username, Status status);
}
