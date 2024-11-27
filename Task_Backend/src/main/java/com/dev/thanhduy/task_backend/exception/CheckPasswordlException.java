package com.dev.thanhduy.task_backend.exception;

public class CheckPasswordlException extends RuntimeException{
    private String message;

    public CheckPasswordlException(String message) {
        this.message = message;
    }
}
