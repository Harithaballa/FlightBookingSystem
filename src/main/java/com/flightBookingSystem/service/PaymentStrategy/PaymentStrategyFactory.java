package com.flightBookingSystem.service.PaymentStrategy;

import com.flightBookingSystem.model.PaymentType;

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
