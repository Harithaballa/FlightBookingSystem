package com.flightBookingSystem.repository;

import com.flightBookingSystem.model.Airline;
import com.flightBookingSystem.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.sql.Date;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {

    @Query("SELECT f from Flight f where exists(select t from Trip t where t.flight = f and FUNCTION('DATE',t.departureTime) = :date ) ")
    List<Flight> fetchAllAvailable(@Param("date") Date date);

    @Query("SELECT f from Flight f where f.airline = :airline and exists(select t from Trip t where t.flight = f and FUNCTION('DATE',t.departureTime) = :date) ")
    List<Flight> fetchAllAvailableByAirline(@Param("date") Date date, @Param("airline") Airline airline);

}
