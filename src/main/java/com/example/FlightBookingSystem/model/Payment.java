package com.example.FlightBookingSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JoinColumn(name="booking_id")
    @OneToOne
    Booking booking;

    @Column(nullable = false)
    String status;

    @Column(name = "payment_date",nullable = false)
    LocalDateTime paymentDate;

    @ManyToOne
    @JoinColumn(name = "paid_by",nullable = false)
    User paidBy;

    @Column(name = "payment_intent_id")
    String paymentIntentId;

    @Column(name = "payment_method_id")
    String paymentMethodId;
}
