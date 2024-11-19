package com.example.FlightBookingSystem.Repository;

import com.example.FlightBookingSystem.Model.SeatType;
import com.example.FlightBookingSystem.Model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip,Long>, JpaSpecificationExecutor<Trip> {

    /*@Query("SELECT t from Trip t  Join Seat s where t.source = :source and t.destination = :destination and t.arrivalTime and FUNCTION('DATE',t.arrivalTime) = :startDate and  s.seatType= :seatType GROUP BY t HAVING COUNT(s)>=: numberOfSeats")
    List<Trip> fetchByConditions(@Param("source") String source, @Param("destination") String destination, @Param("startDate") Date startDate,@Param("numberOfSeats") int numberOfSeats,@Param("seatType") SeatType seatType);
    */
}
