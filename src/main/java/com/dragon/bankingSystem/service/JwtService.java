package com.dragon.bankingSystem.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
     String extractUserEmailFromJwt(String token);
     boolean validateToken(String token, UserDetails userDetails);

}
