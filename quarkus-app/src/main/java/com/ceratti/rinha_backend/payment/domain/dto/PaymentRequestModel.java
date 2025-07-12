package com.ceratti.rinha_backend.payment.domain.dto;

import java.util.UUID;

public record PaymentRequestModel(UUID correlationId, double amount, String requestedAt) {

  
}
