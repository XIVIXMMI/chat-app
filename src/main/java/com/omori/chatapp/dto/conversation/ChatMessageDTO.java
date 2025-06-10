package com.omori.chatapp.dto.conversation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omori.chatapp.entity.enums.MessageEnum.ContentType;
import com.omori.chatapp.entity.enums.MessageEnum.MessageStatus;
import com.omori.chatapp.entity.enums.MessageEnum.MessageType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageDTO{
    @JsonIgnore
    String senderUsername;
    String receiverUsername;
    String content;
    ContentType contentType;
    MessageType messageType;
    MessageStatus messageStatus;
    String conversationId;
    LocalDateTime timestamp;
}
