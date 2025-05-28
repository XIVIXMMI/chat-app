package com.omori.chatapp.service.impl;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.omori.chatapp.config.RabbitMQConfig;
import com.omori.chatapp.dto.conversation.ChatMessageDTO;
import com.omori.chatapp.entity.ChatMessage;
import com.omori.chatapp.repository.ChatMessageRepository;
import com.omori.chatapp.service.ChatService;
import com.omori.chatapp.utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

  private final JwtUtils jwtUtils;
  private final ChatMessageRepository chatMessageRepository;
  private final RabbitTemplate rabbitTemplate;
  private final SimpMessagingTemplate messagingTemplate;

  @Override
  public ChatMessageDTO processAndSendMessage(ChatMessageDTO messageDTO, String jwt) {

    String sender = jwtUtils.extractUsername(jwt);

    // Save to MongoDB
    ChatMessage message = ChatMessage.builder()
        .sender(sender)
        .receiver(messageDTO.receiverUsername())
        .content(messageDTO.content())
        .timestamp(LocalDateTime.now())
        .conversationId(messageDTO.conversationId())
        .type(messageDTO.messageType())
        .build();
    chatMessageRepository.save(message);

    // Send via RabbitMQ
    rabbitTemplate.convertAndSend(
        RabbitMQConfig.CHAT_EXCHANGE,
        RabbitMQConfig.CHAT_ROUTING_KEY,
        messageDTO);
    return messageDTO;
  }

  @Override
  public void broadcastMessage(ChatMessageDTO messageDTO) {
    messagingTemplate.convertAndSend(
        "/topic/" + messageDTO.conversationId(),
        messageDTO);
  }
}
