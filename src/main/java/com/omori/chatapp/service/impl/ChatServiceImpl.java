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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final RabbitTemplate rabbitTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public ChatMessageDTO processAndSendMessage(ChatMessageDTO messageDTO) {
        validateMessage(messageDTO);
        ChatMessage savedMessage = saveMessageToMongo(messageDTO);
        messageDTO.setId(savedMessage.getId());
        sendToRabbitMQ(messageDTO);
        return messageDTO;
    }

    private void validateMessage(ChatMessageDTO messageDTO) {
        if (messageDTO.getSenderUsername() == null || messageDTO.getSenderUsername().isBlank()) {
            throw new IllegalArgumentException("Sender user name is required");
        }
        if (messageDTO.getReceiverUsername() == null || messageDTO.getReceiverUsername().isBlank()) {
            throw new IllegalArgumentException("Sender user name is required");
        }
        if (messageDTO.getContent() == null || messageDTO.getContent().isBlank()) {
            throw new IllegalArgumentException("Sender user name is required");
        }
    }

    private ChatMessage saveMessageToMongo(ChatMessageDTO messageDTO) {
        ChatMessage message = ChatMessage.builder()
                .sender(messageDTO.getSenderUsername())
                .receiver(messageDTO.getReceiverUsername())
                .content(messageDTO.getContent())
                .timestamp(LocalDateTime.now())
                .conversationId(messageDTO.getConversationId())
                .messageType(messageDTO.getMessageType())
                .contentType(messageDTO.getContentType())
                .build();
        return chatMessageRepository.save(message);
    }

    private void sendToRabbitMQ(ChatMessageDTO messageDTO){
      ObjectMapper mapper = new ObjectMapper();
      try {
        String json = mapper.writeValueAsString(messageDTO);
        // Send via RabbitMQ using JSON serialization
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.CHAT_EXCHANGE,
                RabbitMQConfig.CHAT_ROUTING_KEY,
                json);
      } catch (JsonProcessingException ex) {
        throw new RuntimeException("Failed to serializable message for message queue");
      }
    }

    @Override
    public void broadcastMessage(ChatMessageDTO messageDTO) {
        messagingTemplate.convertAndSend(
                "/topic/" + messageDTO.getConversationId(),
                messageDTO);
    }
}
