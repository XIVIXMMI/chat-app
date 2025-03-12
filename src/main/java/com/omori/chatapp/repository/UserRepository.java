package com.omori.chatapp.repository;

import com.omori.chatapp.domain.User;
import com.omori.chatapp.domain.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUserName(String userName);

  Optional<User> findByEmail(String email);

  boolean existsByUserName(String userName);

  boolean existsByEmail(String email);

  List<User> findByStatus(UserStatus status);

  @Query("SELECT u FROM User u JOIN u.roomIds r WHERE r = :roomId")
  List<User> findUsersByRoomId(String roomId);

  @Query("SELECT u FROM User u WHERE u.status = 'ONLINE'")
  List<User> findOnlineUsers();
}
