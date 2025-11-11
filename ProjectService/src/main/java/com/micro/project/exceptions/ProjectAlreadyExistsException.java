package com.micro.project.exceptions;
// Will return HTTP 409 CONFLICT.
public class ProjectAlreadyExistsException extends RuntimeException {
    public ProjectAlreadyExistsException(String message) {
        super(message);
    }
}