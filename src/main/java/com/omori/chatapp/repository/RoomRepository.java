package com.omori.chatapp.repository;

import com.omori.chatapp.domain.Room;
import com.omori.chatapp.domain.enums.RoomPrivacy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface RoomRepository extends MongoRepository<Room, String> {
  @Query("{'participantIds': ?0}")
  List<Room> findRoomsByUserId(String userId);

  @Query("{'participantIds': {$all: ?0}}")
  List<Room> findRoomsByParticipantIds(List<String> participantIds);

  @Query("{'type': ?0}")
  List<Room> findRoomsByType(RoomPrivacy type);

  @Query("{'type': 'PRIVATE', 'participantIds': {$all: ?0}}")
  Room findPrivateRoomByParticipantIds(List<String> participantIds);
}
