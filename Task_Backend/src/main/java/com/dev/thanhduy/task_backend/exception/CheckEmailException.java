package com.dev.thanhduy.task_backend.exception;

public class CheckEmailException extends RuntimeException{
    private String message;

    public CheckEmailException(String message) {
        this.message = message;
    }
}
