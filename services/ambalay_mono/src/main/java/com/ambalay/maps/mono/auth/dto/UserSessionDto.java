package com.ambalay.maps.mono.auth.dto;

public record UserSessionDto(
    String token,
    String refreshToken,
    UserDto user
) {}