package com.micro.cast.exception;


public class InvalidCastingException extends RuntimeException {
    public InvalidCastingException(String message) {
        super(message);
    }
}