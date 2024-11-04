package com.dragon.bankingSystem.security;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecretKey;

    @Value("${app.jwt-expiration}")
    private Long expireDuration;

    private String generateToken(Authentication authentication){
        String userName = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + expireDuration);

        return Jwts.builder()
                .setSubject(userName).setIssuedAt(currentDate).setExpiration(expirationDate).signWith(Key()).compact();

    }

    private Key Key() {

        byte [] bytes = Decoders.BASE64.decode(jwtSecretKey);

        return Keys.hmacShaKeyFor(bytes);

    }

    public String userName(String token){
        Claims claims = Jwts.parser().setSigningKey(Key()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public Boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(Key()).build().parse(token);
            return true;

        } catch (ExpiredJwtException | MalformedJwtException | IllegalArgumentException | SecurityException e) {
            throw new RuntimeException(e);
        }


    }
}
