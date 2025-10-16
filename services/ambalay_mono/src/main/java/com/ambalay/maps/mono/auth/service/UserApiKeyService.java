package com.ambalay.maps.mono.auth.service;

import com.ambalay.maps.mono.auth.dto.ApiKeyResponseDto;
import com.ambalay.maps.mono.auth.entity.User;
import com.ambalay.maps.mono.auth.repository.UserRepository;
import com.ambalay.maps.mono.global.exception.NotFoundException;
import com.ambalay.maps.mono.global.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserApiKeyService {

    private final UserRepository userRepository;

    private static final String API_KEY_PREFIX = "AMBALAY_API_";
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
    private static final SecureRandom RANDOM = new SecureRandom();

    @Transactional
    public ApiKeyResponseDto regenerateApiKey(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new UnauthorizedException("USER_NOT_FOUND"));

        String newApiKey = generateApiKey();
        user.setApiKey(newApiKey);
        userRepository.save(user);

        log.info("API key regenerated for user: {}", userEmail);
        return new ApiKeyResponseDto(newApiKey);
    }

    public ApiKeyResponseDto getApiKey(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new UnauthorizedException("USER_NOT_FOUND"));

        if (user.getApiKey() == null) {
            throw new NotFoundException("API_KEY_NOT_GENERATED");
        }

        log.info("API key fetched for user: {}", userEmail);
        return new ApiKeyResponseDto(user.getApiKey());
    }

    public User validateApiKey(String apiKey) {
        if (!apiKey.startsWith(API_KEY_PREFIX)) {
            throw new UnauthorizedException("INVALID_API_KEY");
        }

        return userRepository.findByApiKey(apiKey)
            .orElseThrow(() -> new UnauthorizedException("INVALID_API_KEY"));
    }

    private String generateApiKey() {
        StringBuilder key = new StringBuilder(API_KEY_PREFIX);
        for (int i = 0; i < 24; i++) {
            key.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return key.toString();
    }
}
