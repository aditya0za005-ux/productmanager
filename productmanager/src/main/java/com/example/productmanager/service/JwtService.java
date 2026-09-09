package com.example.productmanager.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET_KEY = "my-super-secret-jwt-key-for-product-manager-application-2026";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;
    public String generateToken(String username,String role){
        return Jwts.builder()
                .subject(username)
                .claim("role",role)
                .issuedAt(new Date())
                .expiration(new Date(
                        System.currentTimeMillis() + EXPIRATION_TIME
                ))
                .signWith(Keys.hmacShaKeyFor(
                        SECRET_KEY.getBytes()
                ))
                .compact();
    }
    public String extractUsername(String token){
            return extractAllClaims(token).getSubject();
    }
    public String extractRole(String token) {
        return extractAllClaims(token)
                .get("role", String.class);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        }
    public boolean validateToken(String token, String username){
        String extractedUsername = extractUsername(token);

        return extractedUsername.equals(username)
                && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }
}
