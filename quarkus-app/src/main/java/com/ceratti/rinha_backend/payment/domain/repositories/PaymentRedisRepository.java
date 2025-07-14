package com.ceratti.rinha_backend.payment.domain.repositories;

import com.ceratti.rinha_backend.payment.domain.models.Payment;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

@ApplicationScoped
public class PaymentRedisRepository {
    
    @Inject
    private RedisDataSource redisDataSource;

    private ValueCommands<String, String> valueCommands() {
        return redisDataSource.value(String.class, String.class);
    }

    public Payment save(Payment payment) {
        String key = payment.getCorrelationId().toString();
        String jsonValue = serializePayment(payment);
        valueCommands().set(key, jsonValue);
        return payment;
    }


    public Optional<Payment> getById(UUID correlationId) {
        String key = correlationId.toString();
        String jsonValue = valueCommands().get(key);

        Payment payment = deserializePayment(jsonValue);
        return Optional.of(payment);
    }

    public Payment update(Payment payment) {
        return save(payment);
    }



    public List<Payment> getAll() {
        var keyCommands = redisDataSource.key(String.class);
        List<String> allKeys = keyCommands.keys("*");
        
        List<Payment> payments = new ArrayList<>(allKeys.size());
        
        for (String key : allKeys) {
            if (isValidUUID(key)) {
                String jsonValue = valueCommands().get(key);
                if (jsonValue != null) {
                    try {
                        Payment payment = deserializePayment(jsonValue);
                        payments.add(payment);
                    } catch (Exception e) {
                        System.err.println("Erro ao deserializar payment com key: " + key);
                    }
                }
            }
        }
        
        return payments;
    }

    private boolean isValidUUID(String str) {
        if (str == null || str.length() != 36) {
            return false;
        }
        
        return str.charAt(8) == '-' && 
            str.charAt(13) == '-' && 
            str.charAt(18) == '-' && 
            str.charAt(23) == '-';
    }


    private String serializePayment(Payment payment) {
        StringBuilder sb = new StringBuilder(128); // botei 128 como tamanho para evitar reallocations
        sb.append("{\"correlationId\":\"")
          .append(payment.getCorrelationId().toString())
          .append("\",\"amount\":")
          .append(payment.getAmount())
          .append(",\"requestedAt\":\"")
          .append(payment.getRequestedAt().toString())
          .append("\"}");
        return sb.toString();
    }

    private Payment deserializePayment(String json) {
        try {
            String correlationIdStr = extractJsonValue(json, "correlationId");
            String amountStr = extractJsonValue(json, "amount");
            String requestedAtStr = extractJsonValue(json, "requestedAt");
            
            UUID correlationId = UUID.fromString(correlationIdStr);
            double amount = Double.parseDouble(amountStr);
            OffsetDateTime requestedAt = OffsetDateTime.parse(requestedAtStr);
            
            return new Payment(correlationId, amount, requestedAt);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deserializar payment: " + json, e);
        }
    }

    private String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) {
            throw new RuntimeException("Campo não encontrado: " + key);
        }
        
        startIndex += searchKey.length();
        
        while (startIndex < json.length() && Character.isWhitespace(json.charAt(startIndex))) {
            startIndex++;
        }
        
        int endIndex;
        if (json.charAt(startIndex) == '"') {
            startIndex++;
            endIndex = json.indexOf('"', startIndex);
            if (endIndex == -1) {
                throw new RuntimeException("Valor string malformado para: " + key);
            }
            return json.substring(startIndex, endIndex);
        } else {
            endIndex = startIndex;
            while (endIndex < json.length() && 
                   (Character.isDigit(json.charAt(endIndex)) || 
                    json.charAt(endIndex) == '.' || 
                    json.charAt(endIndex) == '-' || 
                    json.charAt(endIndex) == '+' || 
                    json.charAt(endIndex) == 'e' || 
                    json.charAt(endIndex) == 'E')) {
                endIndex++;
            }
            return json.substring(startIndex, endIndex);
        }
    }
}