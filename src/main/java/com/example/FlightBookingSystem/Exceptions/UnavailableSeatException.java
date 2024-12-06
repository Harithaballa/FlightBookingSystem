package com.example.FlightBookingSystem.Exceptions;

public class UnavailableSeatException extends  Exception{
    public  UnavailableSeatException(String message){
        super(message);
    }
}
