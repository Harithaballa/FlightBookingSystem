package com.example.FlightBookingSystem.service;

import com.example.FlightBookingSystem.dto.PaymentRequestDto;
import com.example.FlightBookingSystem.model.PaymentType;
import com.example.FlightBookingSystem.repository.PaymentRepository;
import com.example.FlightBookingSystem.service.PaymentStrategy.PaymentStrategy;
import com.example.FlightBookingSystem.service.PaymentStrategy.PaymentStrategyFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentService {

    PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> makePayment(PaymentRequestDto paymentRequestDto, String idempotencyKey) throws Exception {
        PaymentType paymentType = paymentRequestDto.getPaymentType();
        PaymentStrategy paymentStrategy = PaymentStrategyFactory.getPaymentStrategy(paymentType);
        return paymentStrategy.processPayment(paymentRequestDto,idempotencyKey);
    }

    public String getPaymentIntentByBooking(Long bookingId) {
        return paymentRepository.findPaymentIntentByBooking(bookingId);
    }
}
