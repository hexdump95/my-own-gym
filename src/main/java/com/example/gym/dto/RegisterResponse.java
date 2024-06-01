package com.example.gym.dto;

public class RegisterResponse {
    private String accessToken;

    public RegisterResponse() {
    }

    public RegisterResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
