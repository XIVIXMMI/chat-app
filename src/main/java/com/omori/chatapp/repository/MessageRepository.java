package com.omori.chatapp.repository;

import com.omori.chatapp.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
  @Query("{'roomId': ?0}")
  Page<Message> findByRoomId(String roomId, Pageable pageable);

  @Query("{'roomId': ?0, 'sentAt': {$lt: ?1}}")
  Page<Message> findByRoomIdAndSentAtBefore(String roomId, LocalDateTime before, Pageable pageable);

  @Query("{'content': {$regex: ?0, $options: 'i'}, 'roomId': ?1}")
  Page<Message> searchMessagesByContent(String keyword, String roomId, Pageable pageable);

  @Query("{'senderId': ?0}")
  Page<Message> findBySenderId(String senderId, Pageable pageable);

  @Query("{'roomId': ?0}")
  long countByRoomId(String roomId);

  @Query("{'roomId': ?0, 'readBy': {$ne: ?1}}")
  List<Message> findUnreadMessagesByRoomIdAndUserId(String roomId, String userId);
}
