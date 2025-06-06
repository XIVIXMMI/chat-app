package com.omori.chatapp.mapper;

import com.omori.chatapp.entity.User;
import com.omori.chatapp.dto.user.UserProfileResponseDTO;

public class UserMapper {

    public static UserProfileResponseDTO toUserProfileResponse(User user) {
        return new UserProfileResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getDateOfBirth(),
                user.getAvatarPath(),
                user.getPhoneNumber(),
                user.getStatus(),
                user.getTimeZone(),
                user.getLastActivity(),
                user.isVerified(),
                user.getCreatedAt()
        );
    }
}