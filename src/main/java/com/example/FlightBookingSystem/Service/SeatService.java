package com.example.FlightBookingSystem.Service;

import com.example.FlightBookingSystem.Dto.CreateSeatDto;
import com.example.FlightBookingSystem.Dto.CreateTravellerDto;
import com.example.FlightBookingSystem.Model.Seat;
import com.example.FlightBookingSystem.Model.SeatStatus;
import com.example.FlightBookingSystem.Model.Trip;
import com.example.FlightBookingSystem.Repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    SeatRepository seatRepository;

    TripService tripService;


    @Autowired
    public SeatService(SeatRepository seatRepository,TripService tripService){
        this.seatRepository = seatRepository;
        this.tripService = tripService;
    }

    public Seat add(CreateSeatDto seatDto) throws Exception {
        Seat seat = new Seat();
        seat.setNumber(seatDto.getNumber());
        seat.setType(seatDto.getType());
        seat.setStatus(SeatStatus.AVAILABLE);
        seatRepository.save(seat);
        return  seat;
    }

    public List<Seat> fetchByTrip(Trip trip){
       return seatRepository.findByTrip(trip);
    }

    public void delete(long id) {
        seatRepository.deleteById(id);
    }

    public List<Seat> findAvailableSeats(long trip_id, List<Long> selectedSeats) {
        return seatRepository.findAvailableSeats(trip_id,selectedSeats);
    }

    public void book(Trip trip, List<Long> selectedSeats, List<CreateTravellerDto> travellerDetails) throws Exception {
        List<Seat> availableSeats = fetchByTrip(trip);
        for(long seat_id:selectedSeats){
            Seat seat = seatRepository.findById(seat_id).orElseThrow(()-> new Exception("Seat not found for the id: "+seat_id));
            if(seat.getStatus() == SeatStatus.AVAILABLE){
                seat.setStatus(SeatStatus.OCCUPIED);
            }
        }
    }

    public void reserveSeats(List<Seat> availableSeats) {
        for(Seat seat:availableSeats){
            seat.setStatus(SeatStatus.OCCUPIED);
        }
    }

    public List<Seat> findAllById(List<Long> selectedSeats) {
       return seatRepository.findAllById(selectedSeats);
    }
}
