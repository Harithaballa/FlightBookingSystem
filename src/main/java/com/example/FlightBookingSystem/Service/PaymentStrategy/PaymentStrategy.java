package com.example.FlightBookingSystem.Service.PaymentStrategy;

import com.example.FlightBookingSystem.Dto.PaymentRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PaymentStrategy {
    ResponseEntity<Map<String, Object>> processPayment(PaymentRequestDto paymentRequestDto, String idempotencyKey) throws Exception;
}
