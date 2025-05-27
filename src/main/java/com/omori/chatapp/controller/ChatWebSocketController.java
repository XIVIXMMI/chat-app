package com.omori.chatapp.controller;

import java.util.Map;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.omori.chatapp.dto.conversation.ChatMessageDTO;
import com.omori.chatapp.service.ChatService;

@Controller
public class ChatWebSocketController {

  private final ChatService chatService;

  public ChatWebSocketController(ChatService chatService) {
    this.chatService = chatService;
  }

  @MessageMapping("/chat.send")
  @SendToUser("/queue/messages")
  public ChatMessageDTO sendPrivateMessage(
      ChatMessageDTO messageDTO,
      @Header("simpSessionAttributes") Map<String, Object> sessionAttributes) {
    String jwt = (String) sessionAttributes.get("jwt");
    return chatService.processAndSendMessage(messageDTO, jwt);
  }

  @MessageMapping("/chat.group")
  public void sendGroupMessage(ChatMessageDTO messageDTO) {
    chatService.broadcastMessage(messageDTO);
  }
}
