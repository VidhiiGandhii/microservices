package com.micro.project.exceptions;


// Thrown when project data is invalid (e.g., missing title, 0 episodes).
// Will return HTTP 400 BAD_REQUEST.
public class InvalidProjectException extends RuntimeException {
    public InvalidProjectException(String message) {
        super(message);
    }
}