package com.ceratti.rinha_backend.payment.domain.mappers;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.ceratti.rinha_backend.payment.domain.dto.PaymentRequestModel;
import com.ceratti.rinha_backend.payment.domain.dto.PaymentResponseModel;
import com.ceratti.rinha_backend.payment.domain.models.Payment;

public class PaymentMapper {

    public static Payment toDomain(PaymentRequestModel request) {
        return new Payment(
            request.correlationId(),
            request.amount(),
            OffsetDateTime.parse(request.requestedAt())
        );
    }

    public static PaymentResponseModel toDto(Payment payment) {
        return new PaymentResponseModel(
            payment.getCorrelationId(),
            payment.getAmount(),
            payment.getRequestedAt().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        );
    }
    
}
