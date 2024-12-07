package com.flightBookingSystem.exceptions;

public class UnavailableFlightException extends Exception{
    public UnavailableFlightException(String message){
        super(message);
    }
}
