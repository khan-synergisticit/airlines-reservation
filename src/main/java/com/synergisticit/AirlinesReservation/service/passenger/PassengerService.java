package com.synergisticit.AirlinesReservation.service.passenger;

import com.synergisticit.AirlinesReservation.domain.PageInfo;
import com.synergisticit.AirlinesReservation.domain.Passenger;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PassengerService {
    Passenger savePassenger(Passenger passenger);
    List<Passenger> savePassengers(List<Passenger> passenger);
    List<Passenger> getAllPassengers();
    Passenger getPassengerById(Long id);
    Passenger updatePassenger(Passenger passenger);
    void deletePassenger(Long id);
    Page<Passenger> findAll(PageInfo pageInfo);
    Long getLastPassengerId();
    Passenger findPassengerByEmail(String email);
    Passenger findByFirstNameAndId(String firstName, String id);
}
