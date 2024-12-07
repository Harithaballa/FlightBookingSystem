package com.example.FlightBookingSystem.dto;

import com.example.FlightBookingSystem.model.Currency;
import com.example.FlightBookingSystem.model.TravellerDetails;
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

    double amount;

    Currency currency;

    List<Long> selectedSeats;

    List<TravellerDetails> travellerDetails;
}
