package com.omori.chatapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.omori.chatapp.dto.AuthResponseDTO;
import com.omori.chatapp.dto.LoginRequestDTO;
import com.omori.chatapp.dto.RegisterRequestDTO;
import com.omori.chatapp.service.impl.AuthServiceImpl;
import com.omori.chatapp.service.impl.UserDetailsServiceImpl;
import com.omori.chatapp.utils.JwtUtils;

import jakarta.validation.Valid;

/**
 * AuthController
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthServiceImpl authServiceImpl;
  private final UserDetailsServiceImpl userDetailsSeviceImpl;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  @Autowired
  public AuthController(UserDetailsServiceImpl userDetailsSeviceImpl,
      AuthServiceImpl authServiceImpl,
      AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
    this.userDetailsSeviceImpl = userDetailsSeviceImpl;
    this.authServiceImpl = authServiceImpl;
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
    authServiceImpl.registerUser(request);
    return ResponseEntity.ok("User registered successfully!!");
  }

  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    UserDetails userDetails = userDetailsSeviceImpl.loadUserByUsername(request.getUsername());
    String token = jwtUtils.generateToken(userDetails.getUsername());

    return ResponseEntity.ok(new AuthResponseDTO(token));
  }

}
