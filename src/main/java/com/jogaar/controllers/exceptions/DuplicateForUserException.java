package com.jogaar.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicateForUserException extends ResponseStatusException {
    public DuplicateForUserException() {
        super(HttpStatus.CONFLICT, "resource can't be duplicated for a single user");
    }
}
