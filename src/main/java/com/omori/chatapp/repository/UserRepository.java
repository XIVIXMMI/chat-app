package com.omori.chatapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import com.omori.chatapp.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  Optional<User> findById(Long id);

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);

  boolean existsById(Long id);

  Optional<User> findByEmailAndDeletedAtIsNull(String email);

  Optional<User> findByUsernameAndDeletedAtIsNull(String username);

  Optional<User> findByIdAndDeletedAtIsNull(Long id);

  List<User> findAllByDeletedAtIsNull();

  boolean existsByEmailAndDeletedAtIsNull(String email);

  boolean existsByUsernameAndDeletedAtIsNull(String username);

}
