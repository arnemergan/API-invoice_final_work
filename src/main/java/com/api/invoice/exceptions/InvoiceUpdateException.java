package com.api.invoice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvoiceUpdateException extends RuntimeException {
    public InvoiceUpdateException(String message) {
        super(message);
    }
}
