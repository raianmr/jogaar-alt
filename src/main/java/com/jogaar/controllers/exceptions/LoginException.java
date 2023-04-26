package com.jogaar.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginException extends ResponseStatusException {
    public LoginException() {
        super(HttpStatus.UNAUTHORIZED, "given credentials are invalid");
    }
}
