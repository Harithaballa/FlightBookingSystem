package com.flightBookingSystem.service.PaymentStrategy;

import com.flightBookingSystem.dto.PaymentRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PaymentStrategy {
    ResponseEntity<Map<String, Object>> processPayment(PaymentRequestDto paymentRequestDto, String idempotencyKey) throws Exception;
}
