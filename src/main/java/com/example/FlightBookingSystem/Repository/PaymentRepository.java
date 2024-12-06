package com.example.FlightBookingSystem.Repository;

import com.example.FlightBookingSystem.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByPaymentIntentId(String id);

    @Query("Select p.paymentIntentId from Payment p where p.booking_id = :bookingId ")
    String findPaymentIntentByBooking(@Param("bookingId") Long bookingId);
}
