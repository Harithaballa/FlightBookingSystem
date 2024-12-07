package com.example.FlightBookingSystem.service;

import com.example.FlightBookingSystem.dto.BookingResponse;
import com.example.FlightBookingSystem.dto.CancellationResponse;
import com.example.FlightBookingSystem.dto.CreateBookingDto;
import com.example.FlightBookingSystem.exceptions.UnavailableSeatException;
import com.example.FlightBookingSystem.model.*;
import com.example.FlightBookingSystem.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    TripService tripService;

    UserService userService;

    SeatService seatService;

    BookingRepository bookingRepository;

    RefundService refundService;

    DistributedLockService distributedLockService;

    @Autowired
    public  BookingService(TripService tripService,UserService userService,SeatService seatService,
                           BookingRepository bookingRepository,
                           RefundService refundService,DistributedLockService distributedLockService){
        this.tripService = tripService;
        this.userService = userService;
        this.seatService = seatService;
        this.bookingRepository = bookingRepository;
        this.refundService = refundService;
        this.distributedLockService = distributedLockService;
    }

    @Transactional
    public BookingResponse book(CreateBookingDto createBookingDto) throws UnavailableSeatException,Exception {
        boolean lockAcquired = distributedLockService.tryLockSeats(createBookingDto.getTrip_id(),createBookingDto.getSelectedSeats() );

        if (!lockAcquired) {
            throw new UnavailableSeatException("Another process is booking this seat"); // Another process is booking this seat
        }

        try {
            User user = userService.findById(createBookingDto.getBookedBy());
            Trip trip = tripService.findById(createBookingDto.getTrip_id());

            List<Seat> availableSeats = seatService.findAvailableSeats(createBookingDto.getTrip_id(), createBookingDto.getSelectedSeats());
            if (availableSeats.stream().count() != createBookingDto.getSelectedSeats().stream().count())
                throw new UnavailableSeatException("Some seats are not available");

            seatService.reserveSeats(availableSeats);
            Booking booking = saveBooking(createBookingDto, user, trip, availableSeats);
            return new BookingResponse(booking.getId(),availableSeats,booking.getStatus());
        }
        finally {
            // Always release the lock
            distributedLockService.unlockSeats(createBookingDto.getTrip_id(),createBookingDto.getSelectedSeats() );
        }
    }

    private Booking saveBooking(CreateBookingDto createBookingDto, User user, Trip trip, List<Seat> availableSeats) {
        Booking booking = new Booking();
        booking.setBookedBy(user);
        booking.setBookedSeats(availableSeats);
        booking.setTrip(trip);
        booking.setStatus(BookingStatus.IN_PROGRESS);
        booking.setTravellerDetails(createBookingDto.getTravellerDetails());
        booking.setBookingDate(LocalDateTime.now());
        booking.setAmount(createBookingDto.getAmount());
        booking.setCurrency(createBookingDto.getCurrency());
        bookingRepository.save(booking);
        return booking;
    }

    public Booking findById(long id) throws Exception {
        return bookingRepository.findById(id).orElseThrow(()->new Exception("Booking is not found for the id: "+id));
    }

    @Transactional
    public void save(Booking booking) {
        bookingRepository.save(booking);
    }

    public CancellationResponse cancelBooking(long id) throws Exception {
        Booking booking = bookingRepository.findById(id).orElseThrow(()->new Exception("Booking is not found for the id: "+id));

        if(booking.getStatus() == BookingStatus.CANCELLED){
            throw new Exception("Already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

         double refundAmount =  refundService.initiate(booking);

        return new CancellationResponse(booking.getId(),refundAmount);
    }

    public List<Booking> findByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
}
