package com.example.FlightBookingSystem.Dto;

import com.example.FlightBookingSystem.Model.Currency;
import com.example.FlightBookingSystem.Model.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {

    double amount;

    Currency currency;

    PaymentType paymentType;

    long user_id;

    Map<String, Object> paymentDetails;

}
