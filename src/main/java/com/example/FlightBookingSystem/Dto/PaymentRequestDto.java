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

    String payment_method_id;

    long booking_id;

    long user_id;

    Map<String, Object> paymentDetails;

    PaymentType paymentType;
}
