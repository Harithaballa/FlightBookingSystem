package com.example.FlightBookingSystem.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("CreditCard")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardMethod extends PaymentMethod {
    private String cardNumber;
    private String cardHolderName;
    private LocalDateTime expirationDate;
    private String cardType;
    private String billingAddress;
}
