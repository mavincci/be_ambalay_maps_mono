package com.ambalay.maps.mono.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SigninDto(
    @NotBlank(message = "EMAIL_IS_REQUIRED") @Email(message = "EMAIL_IS_INVALID") String email,
    @NotBlank(message = "PASSWORD_IS_REQUIRED") String password
) {
    public SigninDto {
        email = email != null ? email.trim().toLowerCase() : null;
    }
}