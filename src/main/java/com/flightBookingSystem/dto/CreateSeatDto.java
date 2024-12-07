package com.flightBookingSystem.dto;

import com.flightBookingSystem.model.SeatType;
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
