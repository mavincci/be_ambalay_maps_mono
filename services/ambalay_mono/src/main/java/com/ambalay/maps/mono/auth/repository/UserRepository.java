package com.ambalay.maps.mono.auth.repository;

import com.ambalay.maps.mono.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPublicId(String publicId);
    Optional<User> findByApiKey(String apiKey);
    boolean existsByEmail(String email);
}