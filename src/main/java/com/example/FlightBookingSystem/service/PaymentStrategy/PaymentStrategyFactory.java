package com.example.FlightBookingSystem.service.PaymentStrategy;

import com.example.FlightBookingSystem.model.PaymentType;

public class PaymentStrategyFactory {
    public static PaymentStrategy getPaymentStrategy(PaymentType paymentType){
        switch (paymentType){
            case CREDIT_CARD:
                return  new CreditCardPaymentStrategy();
            case DEBIT_CARD:
                return new DebitCardPaymentStrategy();
            case UPI:
                return  new UPIPaymentStrategy();
            case NET_BANKING:
                return  new NetBankingPaymentStrategy();
            default:
                throw new IllegalArgumentException("Unsupported payment type: " + paymentType);
        }
    }
}
