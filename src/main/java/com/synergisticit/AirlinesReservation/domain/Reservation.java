package com.synergisticit.AirlinesReservation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "reservation_passengers", joinColumns = @JoinColumn(name = "ticket_number"), inverseJoinColumns = @JoinColumn(name = "passenger_id"))
    private List<Passenger> passengers = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "reservation_flightss", joinColumns = @JoinColumn(name = "ticket_number"), inverseJoinColumns = @JoinColumn(name = "flight_id"))
    private List<Flight> flights = new ArrayList<>();
    private Integer checkedBags;
    private Boolean checkedIn = false;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
