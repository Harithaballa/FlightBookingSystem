package com.example.FlightBookingSystem.service;

import com.example.FlightBookingSystem.dto.GrandTotalRequestDto;
import com.example.FlightBookingSystem.dto.GrandTotalResponseDto;
import com.example.FlightBookingSystem.model.Seat;
import com.example.FlightBookingSystem.model.SeatType;
import com.example.FlightBookingSystem.model.Trip;
import com.example.FlightBookingSystem.model.User;
import com.example.FlightBookingSystem.repository.SeatPricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingService {

    SeatPricingRepository seatPricingRepository;

    UserService userService;

    TripService tripService;

    SeatService seatService;

    @Autowired
    public PricingService(SeatPricingRepository seatPricingRepository,UserService userService,TripService tripService,SeatService seatService){
        this.seatPricingRepository = seatPricingRepository;
        this.userService = userService;
        this.tripService = tripService;
        this.seatService = seatService;
    }

    public Double getPrice(SeatType type){
       return seatPricingRepository.fetchPrice(type);
    }

    public  Double calculateTotal(Double flightCharges, Double taxes){
        return  flightCharges+taxes;
    }

    public  Double calculateTax(Double flightCharges){
        return  (flightCharges*15.0)%100;
    }

    public GrandTotalResponseDto getGrandTotal(GrandTotalRequestDto grandTotalRequestDto) throws Exception {
        User user = userService.findById(grandTotalRequestDto.getUser_id());
        Trip trip = tripService.findById(grandTotalRequestDto.getTrip_id());
        List<Seat> selectedSeats = seatService.findAllById(grandTotalRequestDto.getSelectedSeats());
        Double flightCharges = selectedSeats.stream().map(seat -> getPrice(seat.getType())).reduce(0.0,Double::sum);
        Double taxes = calculateTax(flightCharges); //make it dynamic
        Double total = calculateTotal(flightCharges,taxes);
        return new GrandTotalResponseDto(flightCharges,taxes,total);
    }
}
