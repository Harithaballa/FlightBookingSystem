package com.example.FlightBookingSystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="seat_number")
    String number;

    @Column(nullable = false)
    SeatType type;

    @Column(nullable = false)
    SeatStatus status;

    @ManyToOne
    @JoinColumn(name="trip_id")
    Trip trip;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    Booking booking;

}
