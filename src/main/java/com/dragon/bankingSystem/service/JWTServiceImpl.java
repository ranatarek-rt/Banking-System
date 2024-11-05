package com.dragon.bankingSystem.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService{

    @Value("${app.jwt-secret}")
    private String key;


    @Override
    public String generateToken(String userName) {
        Map<String,Object> claims = new HashMap<>();

        // this will generate the token for each user
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .and().signWith(getKey()).compact();

    }

    @Override
    public String extractUserName(String token) {

        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getKey())
                .build().parseSignedClaims(token).getPayload();
    }


    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        System.out.println("Validating token: " + token);
        if (userDetails == null) {
            System.out.println("UserDetails is null!");
            return false; // or throw an exception
        }
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    private Date extractExpirationDate(String token) {
        return extractClaims(token,Claims::getExpiration);
    }

    //return the secret key that will be used for encryption algorithm
    private SecretKey getKey() {
        byte [] bytes = Decoders.BASE64.decode(key);

        return Keys.hmacShaKeyFor(bytes);
    }




}
