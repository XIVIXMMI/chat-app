package com.omori.chatapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.omori.chatapp.dto.auth.AuthResponseDTO;
import com.omori.chatapp.dto.auth.LoginRequestDTO;
import com.omori.chatapp.dto.auth.RegisterRequestDTO;
import com.omori.chatapp.service.impl.AuthServiceImpl;
import com.omori.chatapp.service.impl.UserDetailsServiceImpl;
import com.omori.chatapp.utils.JwtUtils;

import jakarta.validation.Valid;

/**
 * AuthController
 */
@RestController
@RequestMapping("/api/auth")
@SecurityRequirement(name = "bearer-jwt")
@Tag(name = "Authentication Management", description = "APIs for authenticate user in chat application")
public class AuthController {

  private final AuthServiceImpl authServiceImpl;
  private final UserDetailsServiceImpl userDetailsServiceImpl;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  @Autowired
  public AuthController(UserDetailsServiceImpl userDetailsServiceImpl,
      AuthServiceImpl authServiceImpl,
      AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
    this.userDetailsServiceImpl = userDetailsServiceImpl;
    this.authServiceImpl = authServiceImpl;
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
  }

  @PostMapping("/register")
  @Operation(summary = "register new user", description = "the default role will be 'user' ")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
    authServiceImpl.registerUser(request);
    return ResponseEntity.ok("User registered successfully!!");
  }

  @PostMapping("/login")
  @Operation(summary = "login user", description = "use to login and create authenticate token")
  public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(request.getUsername());
    String token = jwtUtils.generateToken(userDetails.getUsername());

    return ResponseEntity.ok(new AuthResponseDTO(token));
  }

}
