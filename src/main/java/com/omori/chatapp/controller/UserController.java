package com.omori.chatapp.controller;

import com.omori.chatapp.dto.user.UserProfileResponseDTO;
import com.omori.chatapp.dto.user.UserProfileUpdateDTO;
import com.omori.chatapp.dto.user.UserStatusUpdateDTO;
import com.omori.chatapp.mapper.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.*;

import com.omori.chatapp.entity.User;
import com.omori.chatapp.entity.enums.UserEnum.Status;
import com.omori.chatapp.dto.user.PasswordChangeRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omori.chatapp.service.UserActivityService;
import com.omori.chatapp.service.UserService;
import com.omori.chatapp.service.impl.UserActivityServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearer-jwt")
@Tag(name = "Users Management", description = "APIs for management users in chat application")
public class UserController {

  private final UserService userService;
  private final UserActivityServiceImpl userActivityServiceImpl;

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  public UserController(UserService userService, UserActivityServiceImpl userActivityServiceImpl) {
    this.userService = userService;
    this.userActivityServiceImpl = userActivityServiceImpl;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @Operation(summary = "Get All Users", description = "get list of all user")
  public ResponseEntity<Page<User>> getAllUsers(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
    return ResponseEntity.ok(userService.findAllUsers(pageable));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
  @Operation(summary = "Get User By Id", description = "get single user by id of that user")
  public ResponseEntity<UserProfileResponseDTO> getUserById(@PathVariable Long id) {
    User user = userService.findUserById(id);
    UserProfileResponseDTO dto = UserMapper.toUserProfileResponse(user);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/deleted")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @Operation(summary = "Get All Deleted Users", description = "get list of all deleted user")
  public ResponseEntity<Page<User>> getDeletedUser(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
    return ResponseEntity.ok(userService.findDeletedUsers(pageable));
  }

  @PutMapping("/{id}/profile")
  @PreAuthorize("hasAuthority('ROLE_ADMIN') or (authentication.principal.id == #id)")
  @Operation(summary = "Update User Profile", description = "Adjust information for user")
  public ResponseEntity<User> updateUserProfile(
      @PathVariable Long id,
      @Valid @RequestBody UserProfileUpdateDTO updateDTO) {
    User updateUser = userService.updateUserProfile(id, updateDTO);
    return ResponseEntity.ok(updateUser);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @Operation(summary = "Delete Users", description = "disable user account")
  public ResponseEntity<String> deleteUser(@PathVariable Long id) {
    userService.softDeleteUserById(id);
    return ResponseEntity.ok("User deleted successfully!");
  }

  @PostMapping("/{userId}/password")
  @PreAuthorize("hasAuthority('ROLE_ADMIN') or (authentication.principal.id == #userId)")
  @Operation(summary = "Change Password User", description = "change password for own user and admin role could do it too")
  public ResponseEntity<String> changePassword(
      @PathVariable Long userId,
      @Valid @RequestBody PasswordChangeRequestDTO request) {
    userService.changePassword(userId, request);
    return ResponseEntity.ok("Password updated successfully");
  }

  @PatchMapping("/{username}/status")
  @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
  @Operation(summary = "Update user status manually")
  public ResponseEntity<?> updateUserStatus(
      @PathVariable String username,
      @RequestBody UserStatusUpdateDTO dto) {

    Status status = dto.status();
    userActivityServiceImpl.updateUserStatus(username, dto.status());
    messagingTemplate.convertAndSend("/topic/status", new StatusChangedMessage(username, status));
    return ResponseEntity.ok("Status updated successfully");
  }

  public record StatusChangedMessage(String username, Status newStatus) {
  }
}
