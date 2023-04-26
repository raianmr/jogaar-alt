package com.jogaar.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotAllowedException extends ResponseStatusException {
    public NotAllowedException() {
        super(HttpStatus.FORBIDDEN, "not allowed to do that");
    }
}
