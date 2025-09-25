package com.ambalay.maps.mono.auth.dto;

import com.ambalay.maps.mono.auth.entity.Role;
import com.ambalay.maps.mono.auth.entity.User;

import java.util.UUID;

public record UserDto(
    String publicId,
    String email,
    String firstName,
    String middleName,
    String lastName,
    Role role
) {
    public static UserDto fromEntity(User user) {
        return new UserDto(
            user.getPublicId(),
            user.getEmail(),
            user.getFirstName(),
            user.getMiddleName(),
            user.getLastName(),
            user.getRole()
        );
    }
}