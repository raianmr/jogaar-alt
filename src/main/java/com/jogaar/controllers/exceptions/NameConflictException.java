package com.jogaar.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NameConflictException extends ResponseStatusException {
    public NameConflictException() {
        super(HttpStatus.CONFLICT, "duplicated name");
    }
}
