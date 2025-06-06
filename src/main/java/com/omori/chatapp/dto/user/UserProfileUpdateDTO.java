package com.omori.chatapp.dto.user;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

/**
 * UserUpdateDTO
 */
public record UserProfileUpdateDTO(
    @Nullable String fullName,
    @Nullable @Email String email,
    @Nullable @Pattern(regexp = "^\\+?[0-9\\-\\s()]*$") String phoneNumber,
    @Nullable String avatarPath,
    @Nullable String timeZone) {
}
