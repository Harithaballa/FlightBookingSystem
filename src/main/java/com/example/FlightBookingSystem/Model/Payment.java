package com.example.FlightBookingSystem.Model;

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

    @Column(nullable = false)
    double amount;

    @JoinColumn(name="booking_id")
    @OneToOne
    Booking booking;

    @Column(nullable = false)
    PaymentType type;

    @Column(nullable = false)
    PaymentStatus status;

    @Column(name = "payment_date",nullable = false)
    LocalDateTime paymentDate;

    @Column(name = "currency",nullable = false)
    Currency currency;

    @ManyToOne
    @JoinColumn(name = "paid_by",nullable = false)
    User paidBy;

    @ManyToOne
    @JoinColumn(name= "payment_method_id")
    PaymentMethod paymentMethod;
}
