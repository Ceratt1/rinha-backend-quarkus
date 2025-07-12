package com.ceratti.rinha_backend.payment.infra;

import com.ceratti.rinha_backend.payment.domain.dto.PaymentRequestModel;
import com.ceratti.rinha_backend.payment.domain.mappers.PaymentMapper;
import com.ceratti.rinha_backend.payment.domain.models.Payment;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/payments")
public class PaymentController {    

    @POST
    public Response post(PaymentRequestModel request) {
        Payment payment = PaymentMapper.toDomain(request);

        return Response
            .status(Response.Status.CREATED)
            .entity(PaymentMapper.toDto(payment))
            .build();

    }
}
