package com.flightBookingSystem.service.PaymentStrategy;

import com.flightBookingSystem.dto.PaymentIntentRequest;
import com.flightBookingSystem.dto.PaymentRequestDto;
import com.flightBookingSystem.model.Booking;
import com.flightBookingSystem.model.BookingStatus;
import com.flightBookingSystem.model.Payment;
import com.flightBookingSystem.repository.PaymentRepository;
import com.flightBookingSystem.service.BookingService;
import com.flightBookingSystem.service.StripeService;
import com.flightBookingSystem.service.UserService;
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

    public CreditCardPaymentStrategy() {

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
