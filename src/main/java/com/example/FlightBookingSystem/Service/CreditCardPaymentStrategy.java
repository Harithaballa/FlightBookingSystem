package com.example.FlightBookingSystem.Service;

import com.example.FlightBookingSystem.Dto.PaymentRequestDto;
import com.example.FlightBookingSystem.Model.*;
import com.example.FlightBookingSystem.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class CreditCardPaymentStrategy implements PaymentStrategy {

    UserService userService;

    PaymentRepository paymentRepository;

    public CreditCardPaymentStrategy() {
    }

    @Autowired
    public CreditCardPaymentStrategy(UserService userService,PaymentRepository paymentRepository) {
        this.userService = userService;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void processPayment(PaymentRequestDto paymentRequestDto) throws Exception {
        Payment payment = new Payment();
        payment.setStatus(PaymentStatus.STARTED);
        payment.setAmount(paymentRequestDto.getAmount());
        payment.setCurrency(paymentRequestDto.getCurrency());

        CreditCardMethod paymentMethod = new CreditCardMethod();
        paymentMethod.setPaymentType(paymentRequestDto.getPaymentType());
        paymentMethod.setCardNumber(paymentRequestDto.getPaymentDetails().get("CardNumber").toString());
        paymentMethod.setCardHolderName(paymentRequestDto.getPaymentDetails().get("CardHolderName").toString());
        paymentMethod.setCardType(paymentRequestDto.getPaymentDetails().get("CardType").toString());
        paymentMethod.setBillingAddress(paymentRequestDto.getPaymentDetails().get("BillingAddress").toString());
        paymentMethod.setExpirationDate((LocalDateTime) paymentRequestDto.getPaymentDetails().get("ExpiryDate"));
        payment.setPaymentMethod(paymentMethod);

        User user = userService.findById(paymentRequestDto.getUser_id());
        payment.setPaidBy(user);
        payment.setPaymentDate(LocalDateTime.now());

        paymentRepository.save(payment);
    }
}
