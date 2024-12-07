package com.flightBookingSystem.model;

import jakarta.persistence.*;

@Entity
public class SeatPricing {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Long id;

    @Column(name = "type",nullable = false)
    SeatType type;

    @Column(name = "price",nullable = false)
    Double price;
}
