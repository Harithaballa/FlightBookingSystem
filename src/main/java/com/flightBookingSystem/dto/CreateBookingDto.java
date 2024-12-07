package com.flightBookingSystem.dto;

import com.flightBookingSystem.model.Currency;
import com.flightBookingSystem.model.TravellerDetails;
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
