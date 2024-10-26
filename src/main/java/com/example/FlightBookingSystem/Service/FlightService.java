package com.example.FlightBookingSystem.Service;

import com.example.FlightBookingSystem.Dto.CreateFlightDto;
import com.example.FlightBookingSystem.Model.Airline;
import com.example.FlightBookingSystem.Model.Flight;
import com.example.FlightBookingSystem.Model.Trip;
import com.example.FlightBookingSystem.Repository.FlightRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class FlightService {

    FlightRepository flightRepository;

    AirlineService airlineService;

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
}
