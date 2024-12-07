package com.example.FlightBookingSystem.Service;

import com.example.FlightBookingSystem.Dto.LoginUserDto;
import com.example.FlightBookingSystem.Dto.RegisterUserDto;
import com.example.FlightBookingSystem.Model.User;
import com.example.FlightBookingSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository,PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signup(RegisterUserDto registerUserDto) {
        User newUser = new User();
        newUser.setEmail(registerUserDto.getEmail());
        newUser.setName(registerUserDto.getName());
        newUser.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        newUser.setRoles(registerUserDto.getRoles());

        return userRepository.save(newUser);
    }

    public User authenticate(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(),
                        loginUserDto.getPassword()
                )
        );
        return userRepository.findByEmail(loginUserDto.getEmail()).orElseThrow();
    }
}
