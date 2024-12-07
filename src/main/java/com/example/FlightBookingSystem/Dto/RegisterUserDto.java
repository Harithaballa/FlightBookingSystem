package com.example.FlightBookingSystem.Dto;

import com.example.FlightBookingSystem.Model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    String email;

    String password;

    String name;

    Set<Role> roles;
}
