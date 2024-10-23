package com.example.FlightBookingSystem.Repository;

import com.example.FlightBookingSystem.Model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip,Long> {
}
