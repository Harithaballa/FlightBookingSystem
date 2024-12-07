package com.flightBookingSystem.controllers;

import com.flightBookingSystem.dto.CreateSeatDto;
import com.flightBookingSystem.dto.CreateTripDto;
import com.flightBookingSystem.model.SeatType;
import com.flightBookingSystem.model.Trip;
import com.flightBookingSystem.service.TripService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {

    TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN")
    public int add(@Valid @RequestBody CreateTripDto createTripDto) throws Exception {
        int  createdFlightNumber = tripService.add(createTripDto);
        return createdFlightNumber;
    }

    @PostMapping("/batch")
    @PreAuthorize("hasRole('ADMIN")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addTrips(@Valid @RequestBody List<CreateTripDto> createTripDtos) throws Exception {
        tripService.addTrips(createTripDtos);
    }

    @PostMapping("/{id}/seats")
    @PreAuthorize("hasRole('ADMIN")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addSeats(@Valid @PathVariable long id, @RequestBody List<CreateSeatDto> createSeatDtos) throws Exception {
        tripService.addSeats(id,createSeatDtos);
    }

    @GetMapping("/search")
    @Cacheable(value = "flightSearch", key = "T(helpers.com.flightBookingSystem.KeyGenerator).generateKeyForFlightSearch(#source, #destination, #date, #numberOfSeats, #seatType,#airline_name)")
    public ResponseEntity<Page<Trip>> searchByCriteria(@Valid @RequestParam String source, @Valid @RequestParam String destination,
                                                       @Valid @RequestParam Date startDate,
                                                       @Valid @RequestParam(required = false, defaultValue = "1") int numberOfSeats,
                                                       @Valid @RequestParam(required = false) SeatType seatType,
                                                       @Valid @RequestParam(required = false) String airline_name,
                                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                                       @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Trip> trips = tripService.searchByCriteria(source, destination, startDate, numberOfSeats, seatType, airline_name, pageable);
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "trip", key = "#id")
    public ResponseEntity<Trip> findById(@Valid @PathVariable long id) throws Exception {
       Trip trip =  tripService.findById(id);
       return  ResponseEntity.ok(trip);
    }
}
