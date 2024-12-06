package com.example.FlightBookingSystem.Controllers;

import com.example.FlightBookingSystem.Model.Booking;
import com.example.FlightBookingSystem.Model.User;
import com.example.FlightBookingSystem.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User findById(@Valid @PathVariable long id) throws Exception {
        return userService.findById(id);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "user-bookings", key = "#id")
    public List<Booking> fetchAllBookings(@Valid @PathVariable long id) throws Exception {
        return userService.getAllBookings(id);
    }
}
