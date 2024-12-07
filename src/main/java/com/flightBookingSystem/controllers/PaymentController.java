package com.flightBookingSystem.controllers;

import com.flightBookingSystem.dto.PaymentRequestDto;
import com.flightBookingSystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    PaymentService paymentService;

    @Autowired
    public  PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> makePayment(PaymentRequestDto paymentRequestDto, @RequestHeader("Idempotency-Key") String idempotencyKey) throws Exception {
        return paymentService.makePayment(paymentRequestDto,idempotencyKey);
    }
}
