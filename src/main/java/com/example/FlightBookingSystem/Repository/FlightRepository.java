package com.example.FlightBookingSystem.Repository;

import com.example.FlightBookingSystem.Model.Airline;
import com.example.FlightBookingSystem.Model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {

    @Query("SELECT f from Flight where exists(select T from Trip where T.flight = f and FUNCTION('DATE',T.departureTime) = :date ) ")
    List<Flight> fetchAllAvailable(Date date);

    @Query("SELECT f from Flight where f.airline = :airline and exists(select T from Trip where T.flight = f and FUNCTION('DATE',T.departureTime) = :date) ")
    List<Flight> fetchAllAvailableByAirline(Date date, Airline airline);
}
