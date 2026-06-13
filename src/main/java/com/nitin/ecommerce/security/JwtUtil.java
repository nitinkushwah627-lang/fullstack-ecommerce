package com.nitin.ecommerce.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // ✅ Properties file se aa raha hai
    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiry}")
    private long EXPIRY;

    // Token banao
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                    new Date(System.currentTimeMillis() + EXPIRY)
                )
                .signWith(getKey())
                .compact();
    }

    // Email nikalo token se
    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Token sahi hai?
    public boolean isValid(String token) {
        try {
            getEmail(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Secret key banao
    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }
}