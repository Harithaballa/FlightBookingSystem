package com.example.FlightBookingSystem.Repository;

import com.example.FlightBookingSystem.Model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {
}
