package com.example.FlightBookingSystem.Controllers;

import com.example.FlightBookingSystem.Dto.RefundRequest;
import com.example.FlightBookingSystem.Service.RefundService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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
    public void processRefund(@Valid @PathVariable Long id) throws Exception {
        refundService.process(id);
    }
}
