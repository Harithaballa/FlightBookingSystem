package com.example.FlightBookingSystem.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name="flight_id")
    Flight flight;

    @Column(name="arrival_time",nullable = false)
    LocalDateTime arrivalTime;

    @Column(name="departure_time",nullable = false)
    LocalDateTime departureTime;

    @Column(nullable = false)
    String source;

    @Column(nullable = false)
    String destination;

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Seat> seats;

    public Trip() {
    }

    public Trip(Flight flight, LocalDateTime arrivalTime, LocalDateTime departureTime, String source, String destination) {
        this.flight = flight;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.source = source;
        this.destination = destination;
    }

    public Trip(Flight flight, LocalDateTime arrivalTime, LocalDateTime departureTime, String source, String destination, List<Seat> seats) {
        this.flight = flight;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.source = source;
        this.destination = destination;
        this.seats = seats;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Objects.equals(id, trip.id) && Objects.equals(flight, trip.flight) && arrivalTime.equals(trip.arrivalTime) && departureTime.equals(trip.departureTime) && source.equals(trip.source) && destination.equals(trip.destination) && Objects.equals(seats, trip.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flight, arrivalTime, departureTime, source, destination, seats);
    }
}
