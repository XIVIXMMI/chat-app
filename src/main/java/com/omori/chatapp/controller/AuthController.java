package com.omori.chatapp.controller;

import com.omori.chatapp.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private JwtService jwtService;

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestParam String username) {
    String token = jwtService.generateToken(username);
    return ResponseEntity.ok(token);
  }

  @GetMapping("/validate")
  public ResponseEntity<String> validateToken(@RequestParam String token) {
    if (jwtService.isTokenValid(token, jwtService.extractUsername(token))) {
      return ResponseEntity.ok("Token hợp lệ");
    } else {
      return ResponseEntity.status(401).body("Token không hợp lệ hoặc đã hết hạn");
    }
  }
}
