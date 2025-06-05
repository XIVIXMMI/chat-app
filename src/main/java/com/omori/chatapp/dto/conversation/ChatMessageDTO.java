package com.omori.chatapp.dto.conversation;

import com.omori.chatapp.entity.enums.MessageEnum.ContentType;
import com.omori.chatapp.entity.enums.MessageEnum.MessageStatus;
import com.omori.chatapp.entity.enums.MessageEnum.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO implements Serializable {
    private String id;
    private String senderUsername;

    @NotBlank(message = "Receiver username is required")
    private String receiverUsername;

    @NotBlank(message = "Message content is required")
    private String content;

    @NotNull(message = "Message type (DIRECT, GROUP ...) is required")
    private MessageType messageType;

    @NotNull(message = "Content type of message (TEXT,IMAGE ...) is required")
    private ContentType contentType;

    @NotNull(message = "Message status (SENT,DELIVERY,READ) is required")
    private MessageStatus messageStatus;

    private String conversationId;

    private LocalDateTime timestamp;
}
