package com.example.FlightBookingSystem.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="flight_number",nullable = false)
    int number;

    @ManyToOne
    @JoinColumn(name="airline_id")
    Airline airline;

    @OneToMany(mappedBy = "flight", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Trip> trips;

    public Flight(int number,Airline airline, List<Trip> trips) {
        this.number = number;
        this.airline = airline;
        this.trips = trips;
    }

    public Flight() {
    }

    public Flight(int number, List<Seat> seats, Airline airline) {
        this.airline = airline;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return id == flight.id && number == flight.number  && Objects.equals(airline, flight.airline) && Objects.equals(trips, flight.trips);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number,airline, trips);
    }
}
