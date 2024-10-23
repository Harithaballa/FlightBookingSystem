package com.example.FlightBookingSystem.Model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String name;

    @OneToMany(mappedBy = "airline",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<Flight> flights;

    public Airline() {
    }

    public Airline(String name, List<Flight> flights) {
        this.name = name;
        this.flights = flights;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airline airline = (Airline) o;
        return id == airline.id && name.equals(airline.name) && Objects.equals(flights, airline.flights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, flights);
    }

}
