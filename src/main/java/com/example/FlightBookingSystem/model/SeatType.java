package com.example.FlightBookingSystem.model;

public enum SeatType {
    ECONOMY("Economy"),
    FIRST_CLASS("FirstClass"),
    BUSINESS_CLASS("BusinessClass"),
    PREMIUM_ECONOMY("PremiumEconomy");
    private String value;
    private SeatType(String str) {
        value = str;
    }
    public String getValue() {
        return this.value;
    }
}
