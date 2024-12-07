package com.flightBookingSystem.exceptions;

public class UnavailableSeatException extends  Exception{
    public  UnavailableSeatException(String message){
        super(message);
    }
}
