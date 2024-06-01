package com.example.gym.exception;

public class WrongUsernameOrPasswordException extends RuntimeException {
    public WrongUsernameOrPasswordException(String message) {
        super(message);
    }
}
