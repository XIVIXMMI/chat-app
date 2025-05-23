package com.omori.chatapp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

  public ChatWebSocketController() {
  }

  // @MessageMapping("/chat")
  // @SendToUser("/queue/messages")
  // some call funtcion here
}
