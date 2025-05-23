package com.omori.chatapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.omori.chatapp.entity.ChatMessage;

@Repository
/**
 * ChatMessageRepository
 */
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

  List<ChatMessage> findByConversationId(String conversationId);
}
