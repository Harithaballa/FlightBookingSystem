package com.flightBookingSystem.dto;

import com.flightBookingSystem.model.Role;
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
