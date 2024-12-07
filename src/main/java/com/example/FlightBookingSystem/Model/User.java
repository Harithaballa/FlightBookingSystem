package com.example.FlightBookingSystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name="email")
    String email;

    @OneToMany(mappedBy = "paidBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Payment> payments;
}
