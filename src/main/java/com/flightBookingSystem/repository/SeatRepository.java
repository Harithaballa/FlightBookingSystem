package com.flightBookingSystem.repository;

import com.flightBookingSystem.model.Seat;
import com.flightBookingSystem.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {
    List<Seat> findByTrip(Trip trip);

    @Query("SELECT s.status from Seat s where s.id = :seat_id")
    boolean findStatus(@Param("seat_id") long seat_id);

    @Query("SELECT s from Seat s where s.trip = (select t from Trip t where t.id=:trip_id) and s.id in :selectedSeats and s.status = 0")
    List<Seat> findAvailableSeats(@Param("trip_id") long trip_id,@Param("selectedSeats") List<Long> selectedSeats);
}
