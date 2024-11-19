package com.example.FlightBookingSystem.Service.PaymentStrategy;

import com.example.FlightBookingSystem.Dto.PaymentRequestDto;
import com.example.FlightBookingSystem.Model.*;
import com.example.FlightBookingSystem.Repository.PaymentRepository;
import com.example.FlightBookingSystem.Service.BookingService;
import com.example.FlightBookingSystem.Service.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CreditCardPaymentStrategy implements PaymentStrategy {

    UserService userService;

    PaymentRepository paymentRepository;

    BookingService bookingService;

    public CreditCardPaymentStrategy() {
    }

    @Autowired
    public CreditCardPaymentStrategy(UserService userService,PaymentRepository paymentRepository,BookingService bookingService) {
        this.userService = userService;
        this.paymentRepository = paymentRepository;
        this.bookingService = bookingService;
    }

    @Override
    public ResponseEntity<Map<String, Object>> processPayment(PaymentRequestDto paymentRequestDto, String idempotencyKey) throws Exception {

        Map<String, Object> response = new HashMap<>();
        Booking booking = bookingService.findById(paymentRequestDto.getBooking_id());

        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount((long) (booking.getAmount()))
                    .setCurrency(booking.getCurrency().toString())
                    .setPaymentMethod(paymentRequestDto.getPayment_method_id())
                    .setConfirm(true)
                    .build();

            RequestOptions requestOptions = RequestOptions.builder()
                    .setIdempotencyKey(idempotencyKey) // Stripe Idempotency Key
                    .build();

            PaymentIntent intent = PaymentIntent.create(params, requestOptions);

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
}
