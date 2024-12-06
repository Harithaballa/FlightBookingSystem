package com.example.FlightBookingSystem.Dto;


import com.example.FlightBookingSystem.Model.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentIntentRequest {

    String payment_method_id;

    double amount;

    Currency currency;

    String idempotencyKey;
}
