package com.example.FlightBookingSystem.Repository;

import com.example.FlightBookingSystem.Model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirlineRepository extends JpaRepository<Airline,Long> {
    Airline findByName(String airline_name);
}
