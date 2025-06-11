package com.omori.chatapp.controller;

import java.util.Map;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import com.omori.chatapp.dto.conversation.ChatMessageDTO;
import com.omori.chatapp.service.impl.ChatServiceImpl;

@Controller
public class ChatWebSocketController {

  private final ChatServiceImpl chatService;

  public ChatWebSocketController(ChatServiceImpl chatService) {
    this.chatService = chatService;
  }

  @MessageMapping("/chat.send")
  @SendToUser("/queue/messages")
  public ChatMessageDTO sendPrivateMessage(
          ChatMessageDTO messageDTO,
          @Header("simpSessionAttributes") Map<String, Object> sessionAttributes,
          @AuthenticationPrincipal UserDetails userDetails) {
    String jwt = (String) sessionAttributes.get("jwt");
    String senderUsername = userDetails.getUsername();
    return chatService.processAndSendMessage(messageDTO,senderUsername);
  }

  @MessageMapping("/chat.group")
  public void sendGroupMessage(ChatMessageDTO messageDTO) {
    chatService.broadcastMessage(messageDTO);
  }
}
