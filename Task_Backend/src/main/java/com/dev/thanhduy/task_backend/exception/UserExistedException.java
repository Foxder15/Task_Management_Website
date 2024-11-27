package com.dev.thanhduy.task_backend.exception;

public class UserExistedException extends RuntimeException{
    private String message;

    public UserExistedException(String message) {
        this.message = message;
    }
}
