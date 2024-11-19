package com.example.FlightBookingSystem.Controllers;

import com.example.FlightBookingSystem.Dto.BookingResponse;
import com.example.FlightBookingSystem.Dto.CreateBookingDto;
import com.example.FlightBookingSystem.Service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boo")
public class BookingController {

    BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<BookingResponse> book(@Valid  @RequestBody CreateBookingDto createBookingDto) throws Exception {
       BookingResponse bookingResponse = bookingService.book(createBookingDto);
       return ResponseEntity.ok(bookingResponse);
    }
}
