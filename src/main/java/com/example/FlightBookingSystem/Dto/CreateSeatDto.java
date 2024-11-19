package com.example.FlightBookingSystem.Dto;

import com.example.FlightBookingSystem.Model.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSeatDto {
    String number;
    SeatType type;
    int trip_id;
}
