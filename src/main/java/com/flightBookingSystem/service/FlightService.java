package com.flightBookingSystem.service;

import com.flightBookingSystem.dto.CreateFlightDto;
import com.flightBookingSystem.model.Airline;
import com.flightBookingSystem.model.Flight;
import com.flightBookingSystem.model.Trip;
import com.flightBookingSystem.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class FlightService {

    FlightRepository flightRepository;

    AirlineService airlineService;

    @Autowired
    public  FlightService(FlightRepository flightRepository,AirlineService airlineService){
        this.flightRepository = flightRepository;
        this.airlineService = airlineService;
    }

    public void add(CreateFlightDto flight) throws Exception {
        Airline airline = airlineService.fetch(flight.getAirline_id());
        Flight newFlight = new Flight(flight.getNumber(),flight.getSeats(),airline);
        flightRepository.save(newFlight);
    }

    public List<Flight> fetchAvailable(Date date) {
        return flightRepository.fetchAllAvailable(date);
    }

    public List<Flight> fetchAvailableByAirline(Date date, long airline_id) throws Exception {
        Airline airline = airlineService.fetch(airline_id);
        return  flightRepository.fetchAllAvailableByAirline(date,airline);
    }

    public void addTrip(Flight flight, Trip trip) {
        flight.getTrips().add(trip);
        flightRepository.save(flight);
    }
}
