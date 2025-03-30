package com.omori.chatapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import com.omori.chatapp.domain.User;

@Repository
/**
 * UserRepository
 */
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);

  boolean existsById(Long id);

  Optional<User> findById(Long id);

}
