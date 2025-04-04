package com.omori.chatapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.omori.chatapp.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);

  boolean existsById(Long id);

  Optional<User> findByEmailAndDeletedAtIsNull(String email);

  Optional<User> findByUsernameAndDeletedAtIsNull(String username);

  Optional<User> findByIdAndDeletedAtIsNull(Long id);

  List<User> findAllByDeletedAtIsNull();

  boolean existsByEmailAndDeletedAtIsNull(String email);

  boolean existsByUsernameAndDeletedAtIsNull(String username);

  @Query("SELECT u FROM User u WHERE u.id = :id AND u.deletedAt IS NULL")
  Optional<User> findById(@Param("id") Long id);

  @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL")
  Page<User> findAllActiveUsers(Pageable pageable);

  @Query("SELECT u FROM User u WHERE u.deletedAt IS NOT NULL")
  Page<User> findAllDeletedUsers(Pageable pageable);
}
