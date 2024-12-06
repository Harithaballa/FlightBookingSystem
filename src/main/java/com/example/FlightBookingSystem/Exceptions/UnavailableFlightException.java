package com.example.FlightBookingSystem.Exceptions;

public class UnavailableFlightException extends Exception{
    public UnavailableFlightException(String message){
        super(message);
    }
}
