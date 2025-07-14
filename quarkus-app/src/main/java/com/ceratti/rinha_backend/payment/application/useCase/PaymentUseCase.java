package com.ceratti.rinha_backend.payment.application.useCase;

import java.util.List;

import com.ceratti.rinha_backend.payment.domain.models.Payment;
import com.ceratti.rinha_backend.payment.domain.repositories.PaymentRedisRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PaymentUseCase {

    @Inject
    private PaymentRedisRepository redisRepository;


    public Payment createPayment(Payment payment) {
        return redisRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return redisRepository.getAll();
    }

    
}
