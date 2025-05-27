package com.omori.chatapp.service;

import com.omori.chatapp.dto.conversation.ChatMessageDTO;

public interface ChatService {

  ChatMessageDTO processAndSendMessage(ChatMessageDTO messageDTO, String jwt);

  void broadcastMessage(ChatMessageDTO messageDTO);
}
