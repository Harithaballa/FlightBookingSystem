package com.flightBookingSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GrandTotalResponseDto {

    Double flightCharges;

    Double taxes;

    Double totalAmount;
}
