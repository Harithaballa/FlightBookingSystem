package com.example.FlightBookingSystem.Dto;

import com.example.FlightBookingSystem.Model.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GrandTotalRequestDto {

    long user_id;

    long trip_id;

    List<Long> selectedSeats;

}
