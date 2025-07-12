package com.ceratti.rinha_backend.payment.domain.dto;

import java.util.UUID;

import com.ceratti.rinha_backend.payment.exceptions.PaymentBadRequestException;

public record PaymentRequestModel(UUID correlationId, double amount, String requestedAt) {

    public PaymentRequestModel {
        if (correlationId == null) {
            throw new PaymentBadRequestException("Correlation ID cannot be null");
        }
        if (amount <= 0) {
            throw new PaymentBadRequestException("Amount must be greater than zero");
        }
        if (requestedAt == null) {
            throw new PaymentBadRequestException("Requested at cannot be null");
        }
    }

}
