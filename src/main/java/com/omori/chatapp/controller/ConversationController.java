package com.omori.chatapp.controller;

import com.omori.chatapp.dto.conversation.ChatMessageDTO;
import com.omori.chatapp.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class ConversationController {

    private final ChatService chatService;

    public ConversationController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @Operation(summary = "Send messages to another user")
    public ResponseEntity<ChatMessageDTO> sendMessage(
            @Valid @RequestBody ChatMessageDTO messageDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if( auth == null || !auth.isAuthenticated()){
            throw new AccessDeniedException("User is not authenticated");
        }
        messageDTO.setSenderUsername(auth.getName());
        chatService.processAndSendMessage(messageDTO);
        return ResponseEntity.ok(messageDTO);
    }

   /* @GetMapping("/conversations/{conversationId}/messages")
    public ResponseEntity<List<ChatMessageDTO>> getMessagesByConversation(@PathVariable String conversationId) {
        List<ChatMessageDTO> messages = chatService.getMessagesByConversationId(conversationId);
        return ResponseEntity.ok(messages);
    }*/
}
