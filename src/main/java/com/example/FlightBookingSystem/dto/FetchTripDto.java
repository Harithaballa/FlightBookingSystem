package com.example.FlightBookingSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FetchTripDto {

    String source;

    String destination;

    Date startDate;

    int numberOfSeats=1;
}
