package com.flightBookingSystem.service.PaymentStrategy;

import com.flightBookingSystem.dto.PaymentRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class UPIPaymentStrategy implements PaymentStrategy {
    @Override
    public ResponseEntity<Map<String, Object>> processPayment(PaymentRequestDto paymentRequestDto, String idempotencyKey) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
