package com.example.FlightBookingSystem.Service;

import com.example.FlightBookingSystem.Dto.PaymentRequestDto;
import com.example.FlightBookingSystem.Model.PaymentType;
import com.example.FlightBookingSystem.Repository.PaymentRepository;
import com.example.FlightBookingSystem.Service.PaymentStrategy.PaymentStrategy;
import com.example.FlightBookingSystem.Service.PaymentStrategy.PaymentStrategyFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentService {

    PaymentRepository paymentRepository;

    UserService userService;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, UserService userService){
        this.paymentRepository = paymentRepository;
        this.userService = userService;
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
