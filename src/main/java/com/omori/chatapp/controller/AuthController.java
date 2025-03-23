package com.omori.chatapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.omori.chatapp.dto.RegisterRequestDTO;
import com.omori.chatapp.service.AuthService;

import jakarta.validation.Valid;

/**
 * AuthController
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDTO request) {
    String message = authService.registerUser(request);
    return ResponseEntity.ok(message);
  }

}
