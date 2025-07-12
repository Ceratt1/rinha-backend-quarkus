package com.ceratti.rinha_backend.payment.exceptions;

import jakarta.ws.rs.BadRequestException;

public class PaymentBadRequestException extends BadRequestException {
    private final String message;

    public PaymentBadRequestException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
