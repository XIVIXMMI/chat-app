package com.omori.chatapp.service.impl;

import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper mapper;

    @Override
    public ChatMessageDTO processAndSendMessage(ChatMessageDTO messageDTO, String senderUsername) {
        messageDTO.setSenderUsername(senderUsername);
        saveToMongoDB(messageDTO);
        pushToRabbitMQ(messageDTO);
        return messageDTO;
    }

    public void saveToMongoDB(ChatMessageDTO messageDTO){
      ChatMessage message = ChatMessage.builder()
              .sender(messageDTO.getSenderUsername())
              .receiver(messageDTO.getReceiverUsername())
              .content(messageDTO.getContent())
              .contentType(messageDTO.getContentType())
              .messageType(messageDTO.getMessageType())
              .messageStatus(messageDTO.getMessageStatus())
              .timestamp(messageDTO.getTimestamp())
              .build();
      chatMessageRepository.save(message);
    }

    public void pushToRabbitMQ(ChatMessageDTO messageDTO){
      try {
        String json = mapper.writeValueAsString(messageDTO);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.CHAT_EXCHANGE,
                RabbitMQConfig.CHAT_ROUTING_KEY,
                json
        );
      } catch (JsonProcessingException ex) {
        throw new RuntimeException("Failed to serialize message to message queue");

      }
    }

    @Override
    public void broadcastMessage(ChatMessageDTO messageDTO) {
        messagingTemplate.convertAndSend(
                "/topic/" + messageDTO.getConversationId(),
                messageDTO);
    }
}
