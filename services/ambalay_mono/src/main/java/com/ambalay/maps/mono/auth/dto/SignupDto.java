package com.ambalay.maps.mono.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupDto(
    @NotBlank(message = "EMAIL_IS_REQUIRED") @Email(message = "EMAIL_IS_INVALID") String email,
    @NotBlank(message = "PASSWORD_IS_REQUIRED") @Size(min = 8, message = "PASSWORD_IS_TOO_SHORT") String password,
    @NotBlank(message = "FIRST_NAME_IS_REQUIRED") String firstName,
    String middleName,
    @NotBlank(message = "LAST_NAME_IS_REQUIRED") String lastName
) {
    public SignupDto {
        email = email != null ? email.trim().toLowerCase() : null;
        firstName = firstName != null ? firstName.trim() : null;
        middleName = middleName != null ? middleName.trim() : null;
        lastName = lastName != null ? lastName.trim() : null;
    }
}