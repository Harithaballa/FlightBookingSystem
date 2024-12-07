package com.flightBookingSystem.repository;

import com.flightBookingSystem.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByPaymentIntentId(String id);

    @Query("Select p.paymentIntentId from Payment p where p.booking = (select b from Booking b where b.id = :bookingId) ")
    String findPaymentIntentByBooking(@Param("bookingId") Long bookingId);
}
