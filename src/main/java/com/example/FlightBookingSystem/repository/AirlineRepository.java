package com.example.FlightBookingSystem.repository;

import com.example.FlightBookingSystem.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<Airline,Long> {
    Airline findByName(String airline_name);
}
