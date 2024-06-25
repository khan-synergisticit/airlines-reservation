package com.synergisticit.AirlinesReservation.domain;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


//Helper object for searching flights.
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Component
public class Search {

    @Transient
    private Long id;
    private String destination;
    private String origin;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate departureDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate arrivalDate;
    private Long departureFlight;
    private Long arrivalFlight;
    private Boolean isRoundTrip = true;
    private Boolean isBooking = false;
    private Integer numTravelers;

}
