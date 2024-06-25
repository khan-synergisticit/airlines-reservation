package com.synergisticit.AirlinesReservation.dao.passenger;

import com.synergisticit.AirlinesReservation.domain.PageInfo;
import com.synergisticit.AirlinesReservation.domain.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PassengerDao {
    Passenger savePassenger(Passenger passenger);
    List<Passenger> savePassengers(List<Passenger> passenger);
    List<Passenger> getAllPassengers();
    Passenger findPassengerByIdNumber(String id);
    Passenger getPassengerById(Long id);
    Passenger updatePassenger(Passenger passenger);
    void deletePassenger(Long id);
    Page<Passenger> findAll(Pageable pageable);
    Long getLastPassengerId();
    Passenger findPassengerByEmail(String email);
    Passenger findPassengerByIdAndFirstName(String firstName, String idNumber);
}
