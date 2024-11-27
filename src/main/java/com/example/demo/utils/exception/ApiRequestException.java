package com.example.demo.utils.exception;

public class ApiRequestException extends RuntimeException {
    public ApiRequestException(String message) {
        super(message);
    }
}
