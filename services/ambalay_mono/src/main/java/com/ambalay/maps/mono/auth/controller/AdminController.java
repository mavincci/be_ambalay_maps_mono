package com.ambalay.maps.mono.auth.controller;

import com.ambalay.maps.mono.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/users")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasRole('ORG_ADMIN')")
    public ResponseEntity<ApiResponse<String>> getUsers() {
        return ApiResponse.success("Admin access granted", "Users data");
    }

    @GetMapping("/system")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasRole('SYSTEM_FIN') or hasRole('SYSTEM_DEV')")
    public ResponseEntity<ApiResponse<String>> getSystemData() {
        return ApiResponse.success("System access granted", "System data");
    }

    @GetMapping("/finance")
    @PreAuthorize("hasRole('SYSTEM_FIN') or hasRole('ORG_FIN')")
    public ResponseEntity<ApiResponse<String>> getFinanceData() {
        return ApiResponse.success("Finance access granted", "Finance data");
    }
}