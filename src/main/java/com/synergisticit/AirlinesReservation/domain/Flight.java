package com.synergisticit.AirlinesReservation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;
    private String flightNumber;
    @ManyToOne
    private Airlines operatingAirlines;
    private String departureCity;
    private String arrivalCity;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate departureDate;
    private LocalTime departureTime;
    private Double ticketPrice;
    private Integer capacity;
    private Integer booked;

    @OneToMany
    private List<Reservation> reservation = new ArrayList<>();

}