package com.example.FlightBookingSystem.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTripDto {

    String source;

    String Destination;

    LocalDateTime startTime;

    LocalDateTime endTime;

    long airline_id;


}
