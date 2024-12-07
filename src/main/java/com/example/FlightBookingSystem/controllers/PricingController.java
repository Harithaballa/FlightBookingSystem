package com.example.FlightBookingSystem.controllers;

import com.example.FlightBookingSystem.dto.GrandTotalRequestDto;
import com.example.FlightBookingSystem.dto.GrandTotalResponseDto;
import com.example.FlightBookingSystem.service.PricingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PricingController {

    PricingService pricingService;

    @Autowired
    public PricingController(PricingService pricingService){
        this.pricingService = pricingService;
    }

    @PostMapping(name = "/grand-total",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GrandTotalResponseDto> grandTotal(@Valid @RequestBody GrandTotalRequestDto grandTotalRequestDto) throws Exception {
       GrandTotalResponseDto responseDto =  pricingService.getGrandTotal(grandTotalRequestDto);
       return ResponseEntity.ok(responseDto);
    }
}
