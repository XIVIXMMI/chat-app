package com.omori.chatapp.controller;

import com.omori.chatapp.dto.conversation.ChatMessageDTO;
import com.omori.chatapp.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class ConversationController {

    private final ChatService chatService;

    public ConversationController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    @Operation(summary = "Send messages to another user")
    public ResponseEntity<ChatMessageDTO> sendMessage(
            @Valid @RequestBody ChatMessageDTO messageDTO,
            @AuthenticationPrincipal UserDetails userDetails){
        String senderUsername = userDetails.getUsername();
        ChatMessageDTO message =  chatService.processAndSendMessage(messageDTO,senderUsername);
        return ResponseEntity.ok(message);
    }
}
