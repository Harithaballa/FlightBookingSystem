package com.example.FlightBookingSystem.Dto;

import com.example.FlightBookingSystem.Model.TravellerDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingDto {

    long bookedBy;

    long trip_id;

    List<Long> selectedSeats;

    List<TravellerDetails> travellerDetails;
}
