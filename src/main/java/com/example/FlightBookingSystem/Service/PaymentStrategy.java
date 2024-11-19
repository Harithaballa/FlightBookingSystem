package com.example.FlightBookingSystem.Service;

import com.example.FlightBookingSystem.Dto.PaymentRequestDto;

public interface PaymentStrategy {
    void processPayment(PaymentRequestDto paymentRequestDto) throws Exception;
}
