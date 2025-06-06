package com.omori.chatapp.dto.user;

import java.time.LocalDateTime;

import com.omori.chatapp.entity.enums.UserEnum.Status;

public record UserStatusUpdateDTO(Status status) {
}
