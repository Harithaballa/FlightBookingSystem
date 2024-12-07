package com.flightBookingSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long bookingId;

    @Column(nullable = false)
    double amount;

    @Column(name = "currency",nullable = false)
    Currency currency;

    @Column(nullable = false)
    RefundStatus status;

}
