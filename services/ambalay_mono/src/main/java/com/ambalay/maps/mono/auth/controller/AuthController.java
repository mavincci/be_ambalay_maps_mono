package com.ambalay.maps.mono.auth.controller;

import com.ambalay.maps.mono.auth.dto.*;
import com.ambalay.maps.mono.global.dto.ApiResponse;
import com.ambalay.maps.mono.auth.service.AuthService;
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

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDto>> signup(@Valid @RequestBody SignupDto dto) {
        UserDto user = authService.signup(dto);
        return ApiResponse.success(201, "User created successfully", user);
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<UserSessionDto>> signin(@Valid @RequestBody SigninDto dto) {
        UserSessionDto session = authService.signin(dto);
        return ApiResponse.success("Login successful", session);
    }

    @GetMapping("/whoami")
    public ResponseEntity<ApiResponse<UserDto>> whoami(Authentication authentication) {
        UserDto user = authService.getCurrentUser(authentication.getName());
        return ApiResponse.success("User details retrieved", user);
    }
}