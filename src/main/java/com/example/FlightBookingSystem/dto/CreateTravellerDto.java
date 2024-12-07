package com.example.FlightBookingSystem.dto;

import com.example.FlightBookingSystem.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTravellerDto {

    Gender gender;

    String firstName;

    String lastName;

    String email;

    String mobileNumber;

    Date dateOfBirth;

    String nationality;
}
