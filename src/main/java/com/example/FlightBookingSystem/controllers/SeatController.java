package com.example.FlightBookingSystem.controllers;

import com.example.FlightBookingSystem.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seats")
public class SeatController {

    SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService){
        this.seatService = seatService;
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "seats", key = "#id")
    public ResponseEntity<Void> delete(long id){
        seatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
