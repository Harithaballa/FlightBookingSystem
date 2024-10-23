package com.example.FlightBookingSystem.Model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    SeatType type;

    @Column(nullable = false)
    SeatStatus status;

    @ManyToOne
    @JoinColumn(name="flight_id")
    Flight flight;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    Booking booking;

    public Seat() {
    }

    public Seat(SeatType type, SeatStatus status, Flight flight, Booking booking) {
        this.type = type;
        this.status = status;
        this.flight = flight;
        this.booking = booking;
    }

    public SeatType getType() {
        return type;
    }

    public void setType(SeatType type) {
        this.type = type;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return id == seat.id && type == seat.type && status == seat.status && Objects.equals(flight, seat.flight) && Objects.equals(booking, seat.booking);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, status, flight, booking);
    }
}
