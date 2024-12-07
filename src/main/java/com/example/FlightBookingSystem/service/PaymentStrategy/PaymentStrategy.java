package com.example.FlightBookingSystem.service.PaymentStrategy;

import com.example.FlightBookingSystem.dto.PaymentRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PaymentStrategy {
    ResponseEntity<Map<String, Object>> processPayment(PaymentRequestDto paymentRequestDto, String idempotencyKey) throws Exception;
}
