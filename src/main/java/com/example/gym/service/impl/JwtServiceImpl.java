package com.example.gym.service.impl;

import com.example.gym.entity.User;
import com.example.gym.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtServiceImpl implements JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiresAfterMinutes}")
    private long expiresAfterMinutes;

    public String generateToken(User user) {
        List<String> roles = user.getUserRoles().stream().map(ur -> ur.getRole().name()).toList();

        return Jwts.builder()
                .issuer(issuer)
                .subject(user.getUsername())
                .expiration(new Date(new Date().getTime() + 60000L * expiresAfterMinutes))
                .claim("roles", roles)
                .signWith(getSecretKey())
                .compact();
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parser().verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
