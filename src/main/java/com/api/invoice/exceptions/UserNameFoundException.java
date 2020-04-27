package com.api.invoice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserNameFoundException extends RuntimeException {
    public UserNameFoundException(String message) {
        super(message);
    }
}
