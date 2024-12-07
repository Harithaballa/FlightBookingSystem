package com.example.FlightBookingSystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    double amount;

    @Column(name = "currency",nullable = false)
    Currency currency;

    @OneToOne
    @JoinColumn(name="booked_by")
    User bookedBy;

    @OneToOne
    @JoinColumn(name="trip_id")
    Trip trip;

    @OneToMany(mappedBy = "",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Seat> bookedSeats;

    @Column(nullable = false)
    BookingStatus status;

    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<TravellerDetails> travellerDetails;

    @Column(name = "booking_date",nullable = false)
    LocalDateTime bookingDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id.equals(booking.id) && bookedBy.equals(booking.bookedBy) && trip.equals(booking.trip) && Objects.equals(bookedSeats, booking.bookedSeats) && status == booking.status && Objects.equals(travellerDetails, booking.travellerDetails) && bookingDate.equals(booking.bookingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookedBy, trip, bookedSeats, status, travellerDetails, bookingDate);
    }
}
