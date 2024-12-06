package com.example.FlightBookingSystem.Service;

import com.example.FlightBookingSystem.Dto.BookingResponse;
import com.example.FlightBookingSystem.Dto.CreateBookingDto;
import com.example.FlightBookingSystem.Exceptions.UnavailableSeatException;
import com.example.FlightBookingSystem.Model.*;
import com.example.FlightBookingSystem.Repository.BookingRepository;
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

    PricingService pricingService;

    BookingRepository bookingRepository;
    @Autowired
    public  BookingService(TripService tripService,UserService userService,SeatService seatService,
                           PricingService pricingService, BookingRepository bookingRepository){
        this.tripService = tripService;
        this.userService = userService;
        this.seatService = seatService;
        this.pricingService = pricingService;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public BookingResponse book(CreateBookingDto createBookingDto) throws UnavailableSeatException,Exception {
        User user = userService.findById(createBookingDto.getBookedBy());
        Trip trip = tripService.findById(createBookingDto.getTrip_id());

        List<Seat> availableSeats = seatService.findAvailableSeats(createBookingDto.getTrip_id(),createBookingDto.getSelectedSeats());
        if(availableSeats.stream().count()!=createBookingDto.getSelectedSeats().stream().count())
            throw new UnavailableSeatException("Some seats are not available");
        seatService.reserveSeats(availableSeats);
        Booking booking = saveBooking(createBookingDto, user, trip, availableSeats);
        return new BookingResponse(booking.getId(),availableSeats,booking.getStatus());
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

    public void save(Booking booking) {
        bookingRepository.save(booking);
    }
}
