package com.synergisticit.AirlinesReservation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "passenger")

public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;

    private String firstName;
    private String lastName;
    @Column(name = "email")
    private String email;
    private String mobileNumber;
    private Gender gender;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private IdentificationType identificationType;
    private String idNumber;
    @ManyToMany(mappedBy = "passengers")
    private List<Reservation> reservations;

    public Passenger(Long passengerId, String firstName, String lastName, String email, String mobileNumber, Gender gender, LocalDate birthDate, IdentificationType identificationType) {
        this.passengerId = passengerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.birthDate = birthDate;
        this.identificationType = identificationType;
    }

}
