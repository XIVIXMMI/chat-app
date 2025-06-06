package com.omori.chatapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/presence")
public class PresenceRestController {

  private final SimpMessagingTemplate messagingTemplate;

  public PresenceRestController(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  @PostMapping("/test")
  public ResponseEntity<String> testBroadcast(@RequestBody(required = false) String status) {
    if (status == null) {
      return ResponseEntity.badRequest().body("Status cannot be null");
    }
    messagingTemplate.convertAndSend("/topic/status", status);
    return ResponseEntity.ok("Broadcasted: " + status);
  }
}
