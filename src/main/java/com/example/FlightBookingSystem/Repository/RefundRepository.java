package com.example.FlightBookingSystem.Repository;

import com.example.FlightBookingSystem.Model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository extends JpaRepository<Refund,Long> {
}
