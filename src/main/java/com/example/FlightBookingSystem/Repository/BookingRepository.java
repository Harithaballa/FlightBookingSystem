package com.example.FlightBookingSystem.Repository;

import com.example.FlightBookingSystem.Model.Booking;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query("Select b from Booking b where b.bookedBy = (Select u from User u where u.id = :userId)")
    List<Booking> findByUserId(@Valid @Param("userId") Long userId);
}
