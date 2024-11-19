package com.example.FlightBookingSystem.Dto;

import com.example.FlightBookingSystem.Model.Gender;
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
