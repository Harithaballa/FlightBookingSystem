package com.example.FlightBookingSystem.Service;

import com.example.FlightBookingSystem.Dto.PaymentIntentRequest;
import com.example.FlightBookingSystem.Model.RefundStatus;
import com.stripe.Stripe;
import com.stripe.exception.ApiConnectionException;
import com.stripe.exception.RateLimitException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secret.key}")
    private String secretKey;

    public  StripeService(){
        Stripe.apiKey = secretKey;
    }

    public PaymentIntent createPaymentIntent(PaymentIntentRequest request) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) (request.getAmount()))
                .setCurrency(request.getCurrency().toString())
                .setPaymentMethod(request.getPayment_method_id())
                .setConfirm(true)
                .build();

        RequestOptions requestOptions = RequestOptions.builder()
                .setIdempotencyKey(request.getIdempotencyKey()) // Stripe Idempotency Key
                .build();

        return createPaymentIntentWithRetry(params, requestOptions,3);
    }

    public Refund refund(String paymentIntentId, double amount) throws Exception {
        RefundCreateParams refundParams = RefundCreateParams.builder()
                .setPaymentIntent(paymentIntentId)
                .setAmount((long)amount)
                .build();

        Refund refund = Refund.create(refundParams);

        return refund;
    }

    private PaymentIntent createPaymentIntentWithRetry(PaymentIntentCreateParams params, RequestOptions requestOptions, int maxRetries) throws StripeException {
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
