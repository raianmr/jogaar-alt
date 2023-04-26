package com.jogaar.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PledgeAmountConflictException extends ResponseStatusException {
    public PledgeAmountConflictException() {
        super(HttpStatus.CONFLICT, "resource for given pledge amount already exists for the campaign");
    }
}
