package com.example.FlightBookingSystem.Controllers;

import com.example.FlightBookingSystem.Dto.CreateFlightDto;
import com.example.FlightBookingSystem.Model.Flight;
import com.example.FlightBookingSystem.Service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flights")
public class FlightController {

    FlightService flightService;

    public FlightController(FlightService flightService){
        this.flightService = flightService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public void add(@Valid @RequestBody CreateFlightDto flight) throws Exception {
        flightService.add(flight);
    }

}
