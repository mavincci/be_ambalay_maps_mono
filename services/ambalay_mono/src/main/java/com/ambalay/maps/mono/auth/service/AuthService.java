package com.ambalay.maps.mono.auth.service;

import com.ambalay.maps.mono.auth.dto.*;
import com.ambalay.maps.mono.auth.entity.User;
import com.ambalay.maps.mono.auth.entity.UserSession;
import com.ambalay.maps.mono.auth.repository.UserRepository;
import com.ambalay.maps.mono.auth.repository.UserSessionRepository;
import com.ambalay.maps.mono.global.exception.ConflictException;
import com.ambalay.maps.mono.global.exception.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    
    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Value("${jwt.secret:mySecretKey1234567890123456789012}")
    private String jwtSecret;

    @Transactional
    public UserDto signup(SignupDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new ConflictException("EMAIL_IS_ALREADY_TAKEN");
        }

        String hashedPassword = passwordEncoder.encode(dto.password());
        User user = User.builder()
            .email(dto.email())
            .passwordHash(hashedPassword)
            .firstName(dto.firstName())
            .middleName(dto.middleName())
            .lastName(dto.lastName())
            .build();
        user = userRepository.save(user);
        
        return UserDto.fromEntity(user);
    }

    @Transactional
    public UserSessionDto signin(SigninDto dto) {
        User user = userRepository.findByEmail(dto.email())
            .orElseThrow(() -> new UnauthorizedException("CREDENTIALS_ARE_INVALID"));
        
        if (!passwordEncoder.matches(dto.password(), user.getPasswordHash())) {
            throw new UnauthorizedException("CREDENTIALS_ARE_INVALID");
        }

        String token = generateJwt(user);
        String refreshToken = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusDays(30);
        
        UserSession session = UserSession.builder()
            .user(user)
            .refreshToken(refreshToken)
            .expiresAt(expiresAt)
            .build();
        userSessionRepository.save(session);
        
        return new UserSessionDto(token, refreshToken, UserDto.fromEntity(user));
    }

    public UserDto getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UnauthorizedException("USER_NOT_FOUND"));
        return UserDto.fromEntity(user);
    }

    @Transactional
    public void changePassword(String email, ChangePasswordDto dto) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UnauthorizedException("USER_NOT_FOUND"));
        
        if (!passwordEncoder.matches(dto.currentPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("CURRENT_PASSWORD_IS_INVALID");
        }
        
        String newHashedPassword = passwordEncoder.encode(dto.newPassword());
        user.setPasswordHash(newHashedPassword);
        userRepository.save(user);
    }

    private String generateJwt(User user) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        
        return Jwts.builder()
            .claim("email", user.getEmail())
            .claim("role", user.getRole().name())
            .claim("publicId", user.getPublicId())
            .issuedAt(new Date())
            .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
            .signWith(key)
            .compact();
    }
}