package com.example.FlightBookingSystem.dto;

import com.example.FlightBookingSystem.model.BookingStatus;
import com.example.FlightBookingSystem.model.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    Long bookingId;

    List<Seat> availableSeats;

    BookingStatus status;
}
