package com.example.FlightBookingSystem.Controllers;

import com.example.FlightBookingSystem.Dto.CreateFlightDto;
import com.example.FlightBookingSystem.Model.Flight;
import com.example.FlightBookingSystem.Service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService){
        this.flightService = flightService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public void add(@Valid @RequestBody CreateFlightDto flight) throws Exception {
        flightService.add(flight);
    }

    @GetMapping("/available")
    @ResponseStatus(code= HttpStatus.OK)
    @Cacheable(value = "flight_availability", key = "#date")
    public List<Flight>  fetchAvailable(@Valid @RequestParam Date date){
        return flightService.fetchAvailable(date);
    }

    @GetMapping("/available/{airline_id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Cacheable(value = "flight_availability_by_airline", key = "#date")
    public List<Flight>  fetchAvailableByAirline(@Valid @PathVariable long airline_id,@Valid @RequestParam Date date) throws Exception {
        return flightService.fetchAvailableByAirline(date,airline_id);
    }
}
