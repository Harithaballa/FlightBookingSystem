package com.example.FlightBookingSystem.Controllers;

import com.example.FlightBookingSystem.Dto.PaymentRequestDto;
import com.example.FlightBookingSystem.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public void makePayment(PaymentRequestDto paymentRequestDto) throws Exception {
        paymentService.makePayment(paymentRequestDto);
    }
}
