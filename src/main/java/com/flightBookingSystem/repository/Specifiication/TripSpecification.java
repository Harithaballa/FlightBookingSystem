package com.flightBookingSystem.repository.Specifiication;

import com.example.FlightBookingSystem.model.*;
import com.flightBookingSystem.model.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TripSpecification {
    public static Specification<Trip> getTripsByCriteria(String source, String destination,
                                                         Date departureTime, int numberOfSeats,
                                                         SeatType seatType, Airline airline) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("source"), source));
            predicates.add(criteriaBuilder.equal(root.get("destination"), destination));
            Expression<Date> departureDate = criteriaBuilder.function("date", Date.class, root.get("departureTime"));
            predicates.add(criteriaBuilder.equal(departureDate, departureTime));

            Join<Trip, Flight> flightJoin = root.join("flight", JoinType.INNER);
            if (airline != null) {
                predicates.add(criteriaBuilder.equal(flightJoin.get("airline"), airline));
            }

            Join<Trip, Seat> seatsJoin = root.join("seats",JoinType.INNER);
            SeatType seatType1 = seatType==null? SeatType.ECONOMY : seatType;
            predicates.add(criteriaBuilder.equal(seatsJoin.get("seatType"),seatType1));

            query.where(predicates.toArray(new Predicate[0]));
            query.groupBy(root.get("id"));
            query.having(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.count(root.get("seats")), numberOfSeats*1L));
            return query.getRestriction();
        };
    }
}
