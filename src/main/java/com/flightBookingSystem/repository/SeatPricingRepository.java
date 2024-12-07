package com.flightBookingSystem.repository;

import com.flightBookingSystem.model.SeatPricing;
import com.flightBookingSystem.model.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatPricingRepository extends JpaRepository<SeatPricing,Long> {

    @Query("SELECT price from SeatPricing sp where sp.type = :type")
    Double fetchPrice(@Param("type") SeatType type);
}
