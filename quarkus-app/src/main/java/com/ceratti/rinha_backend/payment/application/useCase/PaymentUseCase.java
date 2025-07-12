package com.ceratti.rinha_backend.payment.application.useCase;

import com.ceratti.rinha_backend.payment.domain.models.Payment;
import com.ceratti.rinha_backend.payment.domain.repositories.PaymentRedisRepository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class PaymentUseCase {

    @Inject
    private PaymentRedisRepository redisRepository;


    public Payment createPayment(Payment payment) {
        return redisRepository.save(payment);
    }


    
}
