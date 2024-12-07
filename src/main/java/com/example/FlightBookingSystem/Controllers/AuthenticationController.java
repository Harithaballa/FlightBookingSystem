package com.example.FlightBookingSystem.Controllers;

import com.example.FlightBookingSystem.Dto.LoginResponse;
import com.example.FlightBookingSystem.Dto.LoginUserDto;
import com.example.FlightBookingSystem.Dto.RegisterUserDto;
import com.example.FlightBookingSystem.Model.User;
import com.example.FlightBookingSystem.Service.AuthenticationService;
import com.example.FlightBookingSystem.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private JwtTokenHelper jwtTokenHelper;

    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(JwtTokenHelper jwtTokenHelper, AuthenticationService authenticationService) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtTokenHelper.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtTokenHelper.getExpirationDateFromToken(jwtToken));

        return ResponseEntity.ok(loginResponse);
    }
}
