package com.synergisticit.AirlinesReservation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter

@Entity
public class Airlines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airlineId;
    private String airlineName;
    private String airlineCode;
    @OneToMany
    private List<Flight> flightList = new ArrayList<>();

}
