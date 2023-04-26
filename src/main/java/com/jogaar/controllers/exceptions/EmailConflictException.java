package com.jogaar.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailConflictException extends ResponseStatusException {
    public EmailConflictException() {
        super(HttpStatus.CONFLICT, "user with given email already exists");
    }
}
