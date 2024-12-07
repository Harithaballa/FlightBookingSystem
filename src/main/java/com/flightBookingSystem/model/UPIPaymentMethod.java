package com.flightBookingSystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("UPI")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UPIPaymentMethod extends  PaymentMethod {

    @Column(name = "UPI_Id",nullable = false)
    String UPIId;

}
