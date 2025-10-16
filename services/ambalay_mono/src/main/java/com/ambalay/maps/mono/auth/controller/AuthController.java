package com.ambalay.maps.mono.auth.controller;

import com.ambalay.maps.mono.auth.dto.*;
import com.ambalay.maps.mono.global.dto.ApiResponse;
import com.ambalay.maps.mono.auth.service.AuthService;
import com.ambalay.maps.mono.auth.service.UserApiKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final UserApiKeyService userApiKeyService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDto>> signup(@Valid @RequestBody SignupDto dto) {
        UserDto user = authService.signup(dto);
        return ApiResponse.success(201, "USER_CREATE_SUCCESS", user);
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<UserSessionDto>> signin(@Valid @RequestBody SigninDto dto) {
        UserSessionDto session = authService.signin(dto);
        return ApiResponse.success("USER_SIGNIN_SUCCESS", session);
    }

    @GetMapping("/whoami")
    public ResponseEntity<ApiResponse<UserDto>> whoami(Authentication authentication) {
        UserDto user = authService.getCurrentUser(authentication.getName());
        return ApiResponse.success("USER_FETCH_SUCCESS", user);
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @Valid @RequestBody ChangePasswordDto dto,
            Authentication authentication) {
        authService.changePassword(authentication.getName(), dto);
        return ApiResponse.success("PASSWORD_CHANGE_SUCCESS", null);
    }

    @PostMapping("/regenerate-api-key")
    public ResponseEntity<ApiResponse<ApiKeyResponseDto>> regenerateApiKey(Authentication authentication) {
        ApiKeyResponseDto apiKey = userApiKeyService.regenerateApiKey(authentication.getName());
        return ApiResponse.success("API_KEY_REGENERATE_SUCCESS", apiKey);
    }

    @GetMapping("/api-key")
    public ResponseEntity<ApiResponse<ApiKeyResponseDto>> getApiKey(Authentication authentication) {
        ApiKeyResponseDto apiKey = userApiKeyService.getApiKey(authentication.getName());
        return ApiResponse.success("API_KEY_FETCH_SUCCESS", apiKey);
    }
}
