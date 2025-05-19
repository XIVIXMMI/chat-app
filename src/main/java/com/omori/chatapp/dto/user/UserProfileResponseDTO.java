package com.omori.chatapp.dto.user;

import java.time.LocalDateTime;
import com.omori.chatapp.entity.enums.UserEnum.Status;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO for public profile user data")
public record UserProfileResponseDTO(
    @Schema(example = "1") Long id,
    @Schema(example = "example@email.com") String email,
    @Schema(example = "John Doe") String fullName,
    @Schema(example = "01-01-1996") LocalDateTime dateOfBirth,
    @Schema(example = "upload/avatarpath") String avatarPath,
    @Schema(example = "+84123456789") String phoneNumber,
    @Schema(example = "OFFLINE") Status status,
    @Schema(example = "Asia/Ho_Chi_Minh") String timeZone,
    @Schema(example = "2025-04-03 17:32:09") LocalDateTime lastActivity,
    @Schema(example = "False") boolean isVerified,
    @Schema(example = "2025-03-31 19:12:25") LocalDateTime createdAt) {
}
