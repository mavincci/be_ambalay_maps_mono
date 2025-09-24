package com.ambalay.maps.mono.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ambalay.maps.mono.global.dto.ApiResponse;

import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> health() {
        return ApiResponse.success(
                "HEALTHY",
                Map.of(
                        "status", "UP",
                        "service", "ambalay-mono"));
    }
}