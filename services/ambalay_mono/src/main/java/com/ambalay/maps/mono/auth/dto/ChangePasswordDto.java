package com.ambalay.maps.mono.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordDto(
    @NotBlank(message = "CURRENT_PASSWORD_IS_REQUIRED") String currentPassword,
    @NotBlank(message = "NEW_PASSWORD_IS_REQUIRED") @Size(min = 8, message = "NEW_PASSWORD_IS_TOO_SHORT") String newPassword
) {}