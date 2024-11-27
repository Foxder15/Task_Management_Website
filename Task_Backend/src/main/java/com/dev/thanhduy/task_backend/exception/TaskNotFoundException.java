package com.dev.thanhduy.task_backend.exception;

public class TaskNotFoundException extends RuntimeException{
    private String message;

    public TaskNotFoundException(String message) {
        this.message = message;
    }
}
