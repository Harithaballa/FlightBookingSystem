package com.flightBookingSystem.service;

import com.flightBookingSystem.dto.PaymentRequestDto;
import com.flightBookingSystem.model.PaymentType;
import com.flightBookingSystem.repository.PaymentRepository;
import com.flightBookingSystem.service.PaymentStrategy.PaymentStrategy;
import com.flightBookingSystem.service.PaymentStrategy.PaymentStrategyFactory;
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
