package com.ceratti.rinha_backend.payment.domain.dto;

import java.util.UUID;

public record PaymentResponseModel(UUID correlationId, double amount, String requestedAt) {

}
