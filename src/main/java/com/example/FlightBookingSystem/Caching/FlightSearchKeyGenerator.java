package com.example.FlightBookingSystem.Caching;

import java.util.StringJoiner;

public class FlightSearchKeyGenerator {
    public static String generateKey(String source, String destination, String startDate, int numberOfSeats, String seatType,String airline_name) {
        StringJoiner key = new StringJoiner("-");
        key.add(source).add(destination).add(startDate);

        if (numberOfSeats != 1) {
            key.add("seats:" + numberOfSeats);
        }
        if (seatType != null && !seatType.isEmpty()) {
            key.add("type:" + seatType);
        }
        if(airline_name!=null && !airline_name.isEmpty()){
            key.add("airline: "+airline_name);
        }
        return key.toString();
    }
}

