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
public class JwtServiceImpl implements JwtService{

    @Value("${app.secret-key}")
    private String secretKey;
    @Override
    public String extractUserEmailFromJwt(String token) {
        //subject is the email or the username
        return extractClaims(token,Claims::getSubject);
    }


    public String generateTokenWithoutExtraClaims(UserDetails userDetails){
        return generateToken(new HashMap<>() , userDetails);
    }

    public String generateToken(Map<String,Object> claims,UserDetails userDetails ) {

        // this will generate the token for each user
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .and().signWith(getKey()).compact();

    }

    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getKey() {
        byte [] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userEmail = extractUserEmailFromJwt(token);
        return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {

        return extractExpirationDate(token).before(new Date());
    }

    private Date extractExpirationDate(String token) {

        return extractClaims(token,Claims::getExpiration);
    }
}
