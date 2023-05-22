package com.jogaar.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BannedException extends ResponseStatusException {
    public BannedException() {
        super(HttpStatus.FORBIDDEN, "current user was banned by a mod");
    }
}
