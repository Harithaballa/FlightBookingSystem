package com.example.FlightBookingSystem.Model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

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

    public Booking() {
    }

    public Booking(User bookedBy, Trip trip, List<Seat> bookedSeats, BookingStatus status) {
        this.bookedBy = bookedBy;
        this.trip = trip;
        this.bookedSeats = bookedSeats;
        this.status = status;
    }

    public User getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(User bookedBy) {
        this.bookedBy = bookedBy;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public List<Seat> getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(List<Seat> bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id == booking.id && Objects.equals(bookedBy, booking.bookedBy) && Objects.equals(trip, booking.trip) && Objects.equals(bookedSeats, booking.bookedSeats) && status == booking.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookedBy, trip, bookedSeats, status);
    }
}
