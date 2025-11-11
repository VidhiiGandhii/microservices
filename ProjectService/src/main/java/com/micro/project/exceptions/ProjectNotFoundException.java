package com.micro.project.exceptions;

// Thrown when a project cannot be found in the database.
// Will return HTTP 404 NOT_FOUND.
public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String message) {
        super(message);
    }
}