package com.ambalay.maps.mono.global.dto;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        LocalDateTime timestamp) {
    public static <T> ResponseEntity<ApiResponse<T>> success(int statusCode, String message, T data) {
        return ResponseEntity
                .status(statusCode)
                .body(new ApiResponse<>(true, message, data, LocalDateTime.now()));
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(int statusCode, String message, T data) {
        return ResponseEntity
                .status(statusCode)
                .body(new ApiResponse<>(false, message, data, LocalDateTime.now()));
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(String message, T data) {
        return success(200, message, data);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(String message) {
        return error(500, message, null);
    }
}
