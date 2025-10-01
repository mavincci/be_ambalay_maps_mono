package com.ambalay.maps.mono.auth.config;

import com.ambalay.maps.mono.auth.entity.User;
import com.ambalay.maps.mono.auth.service.UserApiKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class UserApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final UserApiKeyService userApiKeyService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        String apiKey = extractApiKey(request);
        
        if (apiKey != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                User user = userApiKeyService.validateApiKey(apiKey);
                List<SimpleGrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_" + user.getRole().name()),
                    new SimpleGrantedAuthority("API_KEY_ACCESS")
                );
                UsernamePasswordAuthenticationToken auth = 
                    new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                log.debug("Invalid user API key: {}", e.getMessage());
            }
        }
        
        filterChain.doFilter(request, response);
    }

    private String extractApiKey(HttpServletRequest request) {
        String header = request.getHeader("X-User-API-Key");
        return header != null && header.startsWith("AMBALAY_API_") ? header : null;
    }
}