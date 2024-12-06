package com.example.FlightBookingSystem.Service;

import com.example.FlightBookingSystem.Controllers.UserController;
import com.example.FlightBookingSystem.Model.Booking;
import com.example.FlightBookingSystem.Model.User;
import com.example.FlightBookingSystem.Repository.BookingRepository;
import com.example.FlightBookingSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;

    BookingService bookingService;

    @Autowired
    public  UserService(UserRepository userRepository,BookingService bookingService){
        this.userRepository = userRepository;
        this.bookingService = bookingService;
    }

    public User findById(long id) throws Exception {
        return userRepository.findById(id).orElseThrow(()-> new Exception("user not found for the id: "+id));
    }

    public List<Booking> getAllBookings(long id) throws Exception {
       User user = userRepository.findById(id).orElseThrow(()-> new Exception("user not found for the id: "+id));
       return bookingService.findByUserId(user.getId());
    }
}
