package com.flightBookingSystem.controllers;

import com.flightBookingSystem.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN")
    public ResponseEntity<Void> delete(long id){
        seatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
