package com.dragon.bankingSystem.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String generateToken(String userName);

    String extractUserName(String token);

    boolean validateToken(String token, UserDetails userDetails);
}

