package com.example.FlightBookingSystem.service;

import com.example.FlightBookingSystem.dto.CreateSeatDto;
import com.example.FlightBookingSystem.dto.CreateTripDto;
import com.example.FlightBookingSystem.exceptions.UnavailableFlightException;
import com.example.FlightBookingSystem.model.*;
import com.example.FlightBookingSystem.repository.Specifiication.TripSpecification;
import com.example.FlightBookingSystem.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {

    TripRepository tripRepository;

    AirlineService airlineService;

    FlightService flightService;

    SeatService seatService;


    @Autowired
    public TripService(TripRepository tripRepository,AirlineService airlineService,FlightService flightService,SeatService seatService){
        this.tripRepository = tripRepository;
        this.airlineService  = airlineService;
        this.flightService = flightService;
        this.seatService = seatService;
    }

    public int add(CreateTripDto createTripDto) throws Exception {
        Date startDate = Date.valueOf(createTripDto.getStartTime().toLocalDate());
        List<Flight> availableFlights = flightService.fetchAvailableByAirline(startDate,createTripDto.getAirline_id());
        if(availableFlights!=null) {
            Flight flight = availableFlights.stream().findFirst().get();
            Trip new_trip = new Trip();
            new_trip.setFlight(flight);
            new_trip.setArrivalTime(createTripDto.getStartTime());
            new_trip.setDepartureTime(createTripDto.getEndTime());
            new_trip.setSource(createTripDto.getSource());
            new_trip.setDestination(createTripDto.getDestination());
            tripRepository.save(new_trip);
            flightService.addTrip(flight,new_trip);
            return flight.getNumber();
        }
        else
            throw new UnavailableFlightException("No flights available");
    }

    public void addTrips(List<CreateTripDto> createTripDtos) throws Exception {
        for (CreateTripDto trip: createTripDtos) {
            add(trip);
        }
    }

    public Page<Trip> searchByCriteria(String source, String destination, Date startDate, int numberOfSeats, SeatType seatType, String airline_name,
                            Pageable pageable) {
        Airline airline = airlineService.fetchByName(airline_name);
        return tripRepository.findAll(TripSpecification.getTripsByCriteria(source,destination,startDate,numberOfSeats,seatType,airline),pageable);
    }

    public Trip findById(long id) throws  Exception{
       return tripRepository.findById(id).orElseThrow(()-> new Exception("Trip not found for the id: "+id));
    }

    public void addSeats(long id, List<CreateSeatDto> createSeatDtos) throws Exception {
        Trip trip =  findById(id);
        List<Seat> seats  =  new ArrayList<>();
        for (CreateSeatDto seatDto: createSeatDtos) {
            seats.add(seatService.add(seatDto));
        }
        trip.setSeats(seats);
        tripRepository.save(trip);
    }
}
