package com.flightBookingSystem.dto;

import com.flightBookingSystem.model.Seat;

import java.util.List;

public class CreateFlightDto {
    int number;
    List<Seat> seats;
    long airline_id;

    public CreateFlightDto() {
    }

    public CreateFlightDto(int number, List<Seat> seats, long airline_id) {
        this.number = number;
        this.seats = seats;
        this.airline_id = airline_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public long getAirline_id() {
        return airline_id;
    }

    public void setAirline_id(long airline_id) {
        this.airline_id = airline_id;
    }
}
