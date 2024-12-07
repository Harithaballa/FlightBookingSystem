package com.flightBookingSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TravellerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Gender gender;

    @Column(name = "first_name",nullable = false)
    String firstName;

    @Column(name = "last_name",nullable = false)
    String lastName;

    @Column(nullable = false)
    String email;

    @Column(name = "mobile_number",nullable = false)
    String mobileNumber;

    @Column(name="date_of_birth",nullable = false)
    Date dateOfBirth;

    @Column(nullable = false)
    String nationality;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    Booking booking;
}
