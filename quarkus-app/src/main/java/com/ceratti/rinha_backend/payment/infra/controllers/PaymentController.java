package com.ceratti.rinha_backend.payment.infra.controllers;

import com.ceratti.rinha_backend.payment.application.useCase.PaymentUseCase;
import com.ceratti.rinha_backend.payment.domain.dto.PaymentRequestModel;
import com.ceratti.rinha_backend.payment.domain.mappers.PaymentMapper;
import com.ceratti.rinha_backend.payment.domain.models.Payment;

import jakarta.ws.rs.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/payments")
public class PaymentController {    

    @Inject
    private PaymentUseCase paymentUsecase;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(PaymentRequestModel request) {
        Payment payment = PaymentMapper.toDomain(request);
        payment = paymentUsecase.createPayment(payment);

        return Response
            .status(Response.Status.CREATED)
            .entity(PaymentMapper.toDto(payment))
            .build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok()
        .entity(paymentUsecase.getAllPayments()
            .stream()
            .map(PaymentMapper::toDto)
            .toList())
        .build();
    }


}   
