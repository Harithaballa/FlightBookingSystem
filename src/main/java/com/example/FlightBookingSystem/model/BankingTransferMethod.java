package com.example.FlightBookingSystem.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankingTransferMethod  extends  PaymentMethod{
    private String bankAccountNumber;
    private String bankName;
    private  String IFSCCode;
}
