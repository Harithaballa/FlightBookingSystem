package com.example.FlightBookingSystem.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
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
