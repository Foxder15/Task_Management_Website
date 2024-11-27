package com.dev.thanhduy.task_backend.exception;

public class CommentFailedException extends RuntimeException{
    private String message;

    public CommentFailedException(String message) {
        this.message = message;
    }
}
