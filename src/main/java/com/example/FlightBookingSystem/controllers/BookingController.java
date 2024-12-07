package com.example.FlightBookingSystem.controllers;

import com.example.FlightBookingSystem.dto.BookingResponse;
import com.example.FlightBookingSystem.dto.CancellationResponse;
import com.example.FlightBookingSystem.dto.CreateBookingDto;
import com.example.FlightBookingSystem.exceptions.UnavailableSeatException;
import com.example.FlightBookingSystem.model.Booking;
import com.example.FlightBookingSystem.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<BookingResponse> book(@Valid  @RequestBody CreateBookingDto createBookingDto) throws UnavailableSeatException,Exception {
       BookingResponse bookingResponse = bookingService.book(createBookingDto);
       return ResponseEntity.ok(bookingResponse);
    }

    @PutMapping("{bookingId}/cancel")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<CancellationResponse> cancelBooking(@Valid @PathVariable long bookingId) throws UnavailableSeatException,Exception {
        CancellationResponse response = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    @Cacheable(value = "user-bookings", key = "#userId")
    public List<Booking> fetchAllBookingsByUserId(@Valid @PathVariable long userId) throws Exception {
        return bookingService.findByUserId(userId);
    }
}
