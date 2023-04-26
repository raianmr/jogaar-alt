package com.jogaar.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DeadlineConflictException extends ResponseStatusException {
    public DeadlineConflictException() {
        super(HttpStatus.CONFLICT, "milestone with given deadline already exists for the campaign");
    }
}
