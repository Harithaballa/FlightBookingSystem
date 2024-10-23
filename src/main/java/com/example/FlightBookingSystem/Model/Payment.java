package com.example.FlightBookingSystem.Model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    double amount;

    @JoinColumn(name="booking_id")
    @OneToOne
    Booking booking;

    @Column(nullable = false)
    PaymentType type;

    @Column(nullable = false)
    PaymentStatus status;

    public Payment() {
    }

    public Payment(double amount, Booking booking, PaymentType type, PaymentStatus status) {
        this.amount = amount;
        this.booking = booking;
        this.type = type;
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id && Double.compare(payment.amount, amount) == 0 && Objects.equals(booking, payment.booking) && type == payment.type && status == payment.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, booking, type, status);
    }
}
