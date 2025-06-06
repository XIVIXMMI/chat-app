package com.omori.chatapp.dto.user;

import org.springframework.lang.Nullable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PasswordChangeRequestDTO(
    @Nullable String oldPassword, // require for non-admin users
    @NotBlank @Size(min = 8, message = "Password must be at least 8 characters") @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "Password must contain letters, numbers, and special characters") String newPassword,
    @NotBlank String confirmPassword) {
}
