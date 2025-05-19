package com.omori.chatapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import com.omori.chatapp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
  Optional<User> findByUsername(String username);

  Optional<User> findByIdAndDeletedAtIsNull(Long id);
  Optional<User> findByEmailAndDeletedAtIsNull(String email);
  Optional<User> findByUsernameAndDeletedAtIsNull(String username);

  // Find all user still active (use in findAll)
  @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL")
  Page<User> findAllActiveUsers(Pageable pageable);

  // Find all user have been soft deleted
  @Query("SELECT u FROM User u WHERE u.deletedAt IS NOT NULL")
  Page<User> findAllDeletedUsers(Pageable pageable);

  // Check unique
  boolean existsByEmailAndIdNot(String email, Long id);
  boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

  // Check existed
  boolean existsByEmail(String email);
  boolean existsByUsername(String username);

  // Optional: check by active
  boolean existsByEmailAndDeletedAtIsNull(String email);
  boolean existsByUsernameAndDeletedAtIsNull(String username);
}
