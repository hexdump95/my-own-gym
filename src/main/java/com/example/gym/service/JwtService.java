package com.example.gym.service;

import com.example.gym.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface JwtService {
    String generateToken(User user);
    Jws<Claims> parseToken(String token);
}
