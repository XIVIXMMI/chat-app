package com.omori.chatapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omori.chatapp.service.impl.UserStatusServiceImpl;

@RestController
@RequestMapping("/api/activity")
public class UserActivityController {

  private final UserStatusServiceImpl userActivityServiceImpl;

  public UserActivityController(UserStatusServiceImpl userActivityServiceImpl) {
    this.userActivityServiceImpl = userActivityServiceImpl;
  }

}
