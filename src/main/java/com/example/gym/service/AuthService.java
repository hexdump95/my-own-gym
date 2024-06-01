package com.example.gym.service;

import com.example.gym.dto.LoginRequest;
import com.example.gym.dto.LoginResponse;
import com.example.gym.dto.RegisterRequest;
import com.example.gym.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
}
