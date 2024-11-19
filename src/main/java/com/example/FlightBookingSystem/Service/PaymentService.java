package com.example.FlightBookingSystem.Service;

import com.example.FlightBookingSystem.Dto.PaymentRequestDto;
import com.example.FlightBookingSystem.Model.PaymentType;
import com.example.FlightBookingSystem.Model.User;
import com.example.FlightBookingSystem.Repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void makePayment(PaymentRequestDto paymentRequestDto) throws Exception {
        PaymentType paymentType = paymentRequestDto.getPaymentType();
        PaymentStrategy paymentStrategy = PaymentStrategyFactory.getPaymentStrategy(paymentType);
        paymentStrategy.processPayment(paymentRequestDto);
    }
}
