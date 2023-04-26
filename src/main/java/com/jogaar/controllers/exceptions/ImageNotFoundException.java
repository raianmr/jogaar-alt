package com.jogaar.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ImageNotFoundException extends ResponseStatusException {
    public ImageNotFoundException() {
        super(HttpStatus.NOT_FOUND, "requested image was not found");
    }
}
