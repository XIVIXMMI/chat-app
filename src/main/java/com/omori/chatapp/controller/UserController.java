package com.omori.chatapp.controller;

import com.omori.chatapp.domain.User;
import com.omori.chatapp.dto.PasswordChangeRequestDTO;
import com.omori.chatapp.dto.UserUpdateDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omori.chatapp.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<Page<User>> getAllUsers(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
    return ResponseEntity.ok(userService.findAllUsers(pageable));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN', 'ROLE_USER')")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.findUserById(id));
  }

  @GetMapping("/deleted")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<Page<User>> getDeletedUser(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
    return ResponseEntity.ok(userService.findDeletedUsers(pageable));
  }

  @PutMapping("/{id}/profile")
  @PreAuthorize("hasAuthority('ROLE_ADMIN') or (authentication.principal.id == #id)")
  public ResponseEntity<User> updateUserProfile(
      @PathVariable Long id,
      @Valid @RequestBody UserUpdateDTO updateDTO) {
    User updateUser = userService.updateUserProfile(id, updateDTO);
    return ResponseEntity.ok(updateUser);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> deleteUser(@PathVariable Long id) {
    userService.softDeleteUserById(id);
    return ResponseEntity.ok("User deleted successfully!");
  }

  @PostMapping("/{userId}/password")
  @PreAuthorize("hasAuthority('ROLE_ADMIN') or (authentication.principal.id == #userId)")
  public ResponseEntity<String> changePassword(
      @PathVariable Long userId,
      @Valid @RequestBody PasswordChangeRequestDTO request) {
    userService.changePassword(userId, request);
    return ResponseEntity.ok("Password updated successfully");
  }
}
