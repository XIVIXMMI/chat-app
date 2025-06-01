package com.omori.chatapp.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.omori.chatapp.dto.user.UserStatusUpdateDTO;
import com.omori.chatapp.service.impl.UserStatusServiceImpl;

@Controller
public class PresenceWebSocketController {

  private final UserStatusServiceImpl userActivityServiceImpl;

  public PresenceWebSocketController(UserStatusServiceImpl userActivityServiceImpl) {
    this.userActivityServiceImpl = userActivityServiceImpl;
  }

  @MessageMapping("/status")
  @SendTo("/topic/status")
  public UserStatusUpdateDTO broadcastStatus(UserStatusUpdateDTO updateDTO, Principal principal) {
    String currentUsername = principal.getName();
    userActivityServiceImpl.updateUserStatus(currentUsername, updateDTO.status());
    return updateDTO;
  }
}
