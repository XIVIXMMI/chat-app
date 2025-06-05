package com.omori.chatapp.controller;

import java.security.Principal;
import java.util.Map;

import com.omori.chatapp.entity.enums.MessageEnum.MessageStatus;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.omori.chatapp.dto.conversation.ChatMessageDTO;
import com.omori.chatapp.service.impl.ChatServiceImpl;

@Controller
public class ChatWebSocketController {

  private final ChatServiceImpl chatService;

  public ChatWebSocketController(ChatServiceImpl chatService) {
    this.chatService = chatService;
  }

  /**
   * Handles private messages between users
   *
   * @param messageDTO The message data transfer object
   * @param principal The authenticated user (auto-injected by Spring Security)
   * @return The processed message (sent back to the sender)
   */
  @MessageMapping("/chat.send")
  @SendToUser("/queue/messages")
  public ChatMessageDTO handlePrivateMessage(
          ChatMessageDTO messageDTO,
          Principal principal) {

    messageDTO.setSenderUsername(principal.getName());
    ChatMessageDTO processed = chatService.processAndSendMessage(messageDTO);
    processed.setMessageStatus(MessageStatus.SENT);
    return processed;
  }

  /**
   * Handles group messages broadcast to multiple users
   *
   * @param messageDTO The message data transfer object
   * @param principal The authenticated user (auto-injected by Spring Security)
   */
  @MessageMapping("/chat.group")
  public void handleGroupMessage(
          ChatMessageDTO messageDTO,
          Principal principal) {

    // Set sender from authenticated user
    messageDTO.setSenderUsername(principal.getName());

    // Broadcast to group
    chatService.broadcastMessage(messageDTO);
  }
}
