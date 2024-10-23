package com.example.FlightBookingSystem.Repository;

import com.example.FlightBookingSystem.Model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<Airline,Long> {
}
