package com.example.FlightBookingSystem.handlers;

import com.example.FlightBookingSystem.exceptions.UnavailableFlightException;
import com.example.FlightBookingSystem.exceptions.UnavailableSeatException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<ErrorResponse> handleStripException(StripeException ex){
        if(ex instanceof CardException){
            CardException cardException = (CardException) ex;
            logger.error("Payment failed: %s (Decline code: %s)", ex.getMessage(), cardException.getDeclineCode());
            ErrorResponse response = new ErrorResponse(ex.getMessage(),cardException.getDeclineCode());
            return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
        }
        else if(ex instanceof InvalidRequestException){
            logger.error("Invalid payment request: " , ex.getMessage());
            ErrorResponse response = new ErrorResponse("Invalid payment request: ",ex.getMessage());
            return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ErrorResponse>(new ErrorResponse("Stripe error occurred",ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnavailableSeatException.class)
    public ResponseEntity<ErrorResponse> unavailableSeatException(UnavailableSeatException ex) {
        ErrorResponse error = new ErrorResponse("Seat is already booked", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnavailableFlightException.class)
    public ResponseEntity<ErrorResponse> unavailableFlightsException(UnavailableFlightException ex) {
        ErrorResponse error = new ErrorResponse("There are no flights available", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
