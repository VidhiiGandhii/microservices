package com.micro.cast.exception;

// Thrown when the downstream ProjectService is unreachable.
// Will return HTTP 503 SERVICE_UNAVAILABLE.
public class ProjectServiceOfflineException extends RuntimeException {
    public ProjectServiceOfflineException(String message) {
        super(message);
    }
}