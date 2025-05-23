package com.omori.chatapp.dto.conversation;

import java.time.LocalDateTime;

public record ChatMessageDTO(
    String senderUsername,
    String receiverUsername,
    String content,
    String messageType,
    String conversationId,
    LocalDateTime time) {
}
