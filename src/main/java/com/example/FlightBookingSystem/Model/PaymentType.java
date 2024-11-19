package com.example.FlightBookingSystem.Model;

public enum PaymentType {
    NET_BANKING("NetBanking"),

    UPI("UPI"),

    CREDIT_CARD("CreditCard"),

    DEBIT_CARD("DebitCard");

    private String value;
    private PaymentType(String str) {
        value = str;
    }
    public String getValue() {
        return this.value;
    }
}
