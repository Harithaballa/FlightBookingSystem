package com.example.FlightBookingSystem.Service;

import com.example.FlightBookingSystem.Dto.AirlineNamesDto;
import com.example.FlightBookingSystem.Model.Airline;
import com.example.FlightBookingSystem.Model.Flight;
import com.example.FlightBookingSystem.Repository.AirlineRepository;
import com.okta.commons.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AirlineService {

    AirlineRepository airlineRepository;

    @Autowired
    public AirlineService(AirlineRepository airlineRepository){
        this.airlineRepository = airlineRepository;
    }

    public void add(Airline airline) {
        airlineRepository.save(airline);
    }

    public Airline fetch(long id) throws Exception {
        return airlineRepository.findById(id).orElseThrow(()->new Exception("not found"));
    }

    public List<AirlineNamesDto> fetchAllNames() {
        return airlineRepository.findAll().stream().map(airline -> new AirlineNamesDto(airline.getId(),airline.getName())).collect(Collectors.toList());
    }

    public  List<Airline> fetchAll(){
        return  airlineRepository.findAll();
    }

    public Airline fetchByName(String airline_name) {
        return  airlineRepository.findByName(airline_name);
    }
}
