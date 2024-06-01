package com.example.gym.service.impl;

import com.example.gym.dto.LoginRequest;
import com.example.gym.dto.LoginResponse;
import com.example.gym.dto.RegisterRequest;
import com.example.gym.dto.RegisterResponse;
import com.example.gym.entity.Role;
import com.example.gym.entity.User;
import com.example.gym.entity.UserRole;
import com.example.gym.exception.WrongUsernameOrPasswordException;
import com.example.gym.repository.UserRepository;
import com.example.gym.service.AuthService;
import com.example.gym.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthServiceImpl(JwtService jwtService, BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        boolean userExists = userRepository.existsByUsername(registerRequest.getUsername());
        if (userExists) {
            throw new UsernameNotFoundException("user already exists");
        }
        UserRole userRole = new UserRole();
        userRole.setRole(Role.USER);

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setUserRoles(List.of(userRole));
        userRepository.save(user);

        String accessToken = jwtService.generateToken(user);
        RegisterResponse response = new RegisterResponse();
        response.setAccessToken(accessToken);
        return response;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new WrongUsernameOrPasswordException("wrong username or password");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new WrongUsernameOrPasswordException("wrong username or password");


        String accessToken = jwtService.generateToken(user);
        LoginResponse response = new LoginResponse();
        response.setAccessToken(accessToken);
        return response;
    }

}
