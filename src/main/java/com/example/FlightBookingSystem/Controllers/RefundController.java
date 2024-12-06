package com.example.FlightBookingSystem.Controllers;

import com.example.FlightBookingSystem.Dto.RefundRequest;
import com.example.FlightBookingSystem.Service.RefundService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController("refunds")
public class RefundController {

    RefundService refundService;

    @Autowired
    public  RefundController(RefundService refundService){
        this.refundService = refundService;
    }

    @PostMapping("/calculate")
    public double calculateRefund(@Valid @RequestBody RefundRequest request) throws Exception {
        return refundService.calculate(request);
    }

    @PostMapping("{id}/process")
    public void processRefund(@Valid @PathVariable Long id,@RequestHeader("Idempotency-Key") String idempotencyKey) throws Exception {
        refundService.process(id,idempotencyKey);
    }
}