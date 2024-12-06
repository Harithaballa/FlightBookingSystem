package com.example.FlightBookingSystem.Service.PaymentStrategy;

import com.example.FlightBookingSystem.Dto.PaymentIntentRequest;
import com.example.FlightBookingSystem.Dto.PaymentRequestDto;
import com.example.FlightBookingSystem.Model.Booking;
import com.example.FlightBookingSystem.Model.BookingStatus;
import com.example.FlightBookingSystem.Model.Payment;
import com.example.FlightBookingSystem.Repository.PaymentRepository;
import com.example.FlightBookingSystem.Service.BookingService;
import com.example.FlightBookingSystem.Service.StripeService;
import com.example.FlightBookingSystem.Service.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CreditCardPaymentStrategy implements PaymentStrategy {

    UserService userService;

    PaymentRepository paymentRepository;

    BookingService bookingService;

    StripeService stripeService;

    @Autowired
    public CreditCardPaymentStrategy(UserService userService,PaymentRepository paymentRepository,
                                     BookingService bookingService, StripeService stripeService) {
        this.userService = userService;
        this.paymentRepository = paymentRepository;
        this.bookingService = bookingService;
        this.stripeService = stripeService;
    }

    @Override
    public ResponseEntity<Map<String, Object>> processPayment(PaymentRequestDto paymentRequestDto, String idempotencyKey) throws Exception {

        Map<String, Object> response = new HashMap<>();
        Booking booking = bookingService.findById(paymentRequestDto.getBooking_id());

        try {
            PaymentIntentRequest paymentIntentRequest = getPaymentIntentRequest(paymentRequestDto, idempotencyKey, booking);

            PaymentIntent intent = stripeService.createPaymentIntent(paymentIntentRequest);

            Payment payment = paymentRepository.findByPaymentIntentId(intent.getId())
                    .orElseGet(() -> {
                        Payment newPayment = new Payment();
                        newPayment.setBooking(booking);
                        newPayment.setPaymentIntentId(intent.getId());
                        newPayment.setPaymentMethodId(paymentRequestDto.getPayment_method_id());
                        newPayment.setStatus(intent.getStatus());
                        return paymentRepository.save(newPayment);
                    });

            if ("succeeded".equals(intent.getStatus())) {
                booking.setStatus(BookingStatus.COMPLETED);
                bookingService.save(booking);
            }
            response.put("status", intent.getStatus());
            response.put("bookingStatus", booking.getStatus());
        } catch (StripeException e) {
            response.put("status", "FAILED");
            response.put("error", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    private PaymentIntentRequest getPaymentIntentRequest(PaymentRequestDto paymentRequestDto, String idempotencyKey, Booking booking) {
        PaymentIntentRequest paymentIntentRequest = new PaymentIntentRequest();
        paymentIntentRequest.setAmount(booking.getAmount());
        paymentIntentRequest.setCurrency(booking.getCurrency());
        paymentIntentRequest.setPayment_method_id(paymentRequestDto.getPayment_method_id());
        paymentIntentRequest.setIdempotencyKey(idempotencyKey);
        return paymentIntentRequest;
    }


}
