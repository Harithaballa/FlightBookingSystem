package com.example.FlightBookingSystem.service.PaymentStrategy;

import com.example.FlightBookingSystem.dto.PaymentRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class NetBankingPaymentStrategy implements PaymentStrategy {
    @Override
    public ResponseEntity<Map<String, Object>> processPayment(PaymentRequestDto paymentRequestDto, String idempotencyKey) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
