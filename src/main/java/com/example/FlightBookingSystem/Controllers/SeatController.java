package com.example.FlightBookingSystem.Controllers;

import com.example.FlightBookingSystem.Dto.CreateSeatDto;
import com.example.FlightBookingSystem.Service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Void> delete(long id){
        seatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
