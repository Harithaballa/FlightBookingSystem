package com.example.FlightBookingSystem.Service.PaymentStrategy;

import com.example.FlightBookingSystem.Dto.PaymentRequestDto;
import com.example.FlightBookingSystem.Model.*;
import com.example.FlightBookingSystem.Repository.PaymentRepository;
import com.example.FlightBookingSystem.Service.BookingService;
import com.example.FlightBookingSystem.Service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.ApiConnectionException;
import com.stripe.exception.RateLimitException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CreditCardPaymentStrategy implements PaymentStrategy {

    UserService userService;

    PaymentRepository paymentRepository;

    BookingService bookingService;

    @Value("${stripe.secret.key}")
    private String secretKey;

    public CreditCardPaymentStrategy() {

    }

    @Autowired
    public CreditCardPaymentStrategy(UserService userService,PaymentRepository paymentRepository,BookingService bookingService) {
        this.userService = userService;
        this.paymentRepository = paymentRepository;
        this.bookingService = bookingService;
        Stripe.apiKey = secretKey;
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

            PaymentIntent intent = createPaymentIntentWithRetry(params, requestOptions,3);

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

    private PaymentIntent createPaymentIntentWithRetry(PaymentIntentCreateParams params, RequestOptions requestOptions,int maxRetries) throws StripeException {
        int attempts = 0;
        while (attempts < maxRetries) {
            try {
                return PaymentIntent.create(params,requestOptions);
            } catch (StripeException e) {
                if (isRetryableError(e) && attempts < maxRetries - 1) {
                    attempts++;
                    try {
                        Thread.sleep((long) Math.pow(2, attempts) * 1000); // Exponential backoff
                    } catch (InterruptedException ignored) {

                    }
                } else {
                    throw e;
                }
            }
        }
        throw new RuntimeException("Failed to create payment intent after retries");
    }

    private boolean isRetryableError(StripeException e) {
        return e instanceof ApiConnectionException || e instanceof RateLimitException;
    }

}
