package com.example.FlightBookingSystem.controllers;

import com.example.FlightBookingSystem.model.Refund;
import com.example.FlightBookingSystem.service.RefundService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;


@RestController("refunds")
public class RefundController {

    RefundService refundService;

    @Autowired
    public  RefundController(RefundService refundService){
        this.refundService = refundService;
    }

//    @PostMapping("/calculate")
//    public double calculateRefund(@Valid @RequestBody RefundRequest request) throws Exception {
//        return refundService.calculate(request.getBookingId());
//    }

    @PostMapping("{id}/process")
    public void processRefund(@Valid @PathVariable Long id,@RequestHeader("Idempotency-Key") String idempotencyKey) throws Exception {
        refundService.process(id,idempotencyKey);
    }

    @GetMapping("{id}")
    @Cacheable(value = "refunds",key = "#id")
    public Refund get(@Valid @PathVariable Long id) throws Exception {
        return  refundService.findById(id);
    }
}
