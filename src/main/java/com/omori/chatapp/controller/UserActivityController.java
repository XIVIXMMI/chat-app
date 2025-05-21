package com.omori.chatapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omori.chatapp.service.impl.UserActivityServiceImpl;

@RestController
@RequestMapping("/api/activity")
public class UserActivityController {

  private final UserActivityServiceImpl userActivityServiceImpl;

  public UserActivityController(UserActivityServiceImpl userActivityServiceImpl) {
    this.userActivityServiceImpl = userActivityServiceImpl;
  }

}
