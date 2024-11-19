package com.example.FlightBookingSystem.Controllers;

import com.example.FlightBookingSystem.Dto.AirlineNamesDto;
import com.example.FlightBookingSystem.Model.Airline;
import com.example.FlightBookingSystem.Service.AirlineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airlines")
public class AirlineController {

    AirlineService airlineService;

    @Autowired
    public AirlineController(AirlineService airlineService){
        this.airlineService = airlineService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addAirline(@Valid @RequestBody Airline airline){
        airlineService.add(airline);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Airline fetch(@PathVariable @Valid long id) throws Exception {
        return airlineService.fetch(id);
    }

    @GetMapping("/all")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Airline> fetchAll(){
        return airlineService.fetchAll();
    }

    @GetMapping("/allNames")
    @ResponseStatus(code = HttpStatus.OK)
    public List<AirlineNamesDto> fetchAllNames(){
        return airlineService.fetchAllNames();
    }

}
