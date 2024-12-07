package com.flightBookingSystem.dto;

import com.flightBookingSystem.model.BookingStatus;
import com.flightBookingSystem.model.Seat;
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
