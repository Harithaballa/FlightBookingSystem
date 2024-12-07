package com.flightBookingSystem.service;

import com.flightBookingSystem.model.User;
import com.flightBookingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public  UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findById(long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("user not found for the id: " + id));
    }
}
