package com.synergisticit.AirlinesReservation.service.passenger;

import com.synergisticit.AirlinesReservation.dao.passenger.PassengerDao;
import com.synergisticit.AirlinesReservation.domain.PageInfo;
import com.synergisticit.AirlinesReservation.domain.Passenger;
import com.synergisticit.AirlinesReservation.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service

public class PassengerServiceImpl implements PassengerService {
    @Autowired private PassengerDao passengerDao;
    @Autowired private PassengerRepository passengerRepository;
    @Override
    public Passenger savePassenger(Passenger passenger) {
        return passengerDao.savePassenger(passenger);// passengerDao.findPassengerByIdNumber(passenger.getIdNumber());
    }

    @Override
    public List<Passenger> savePassengers(List<Passenger> passengers) {
        return passengerDao.savePassengers(passengers);
    }

    @Override
    public List<Passenger> getAllPassengers() {
        return passengerDao.getAllPassengers();
    }


    @Override
    public Passenger getPassengerById(Long id) {
        return passengerDao.getPassengerById(id);
    }

    @Override
    public Passenger updatePassenger(Passenger passenger) {
        return passengerDao.updatePassenger(passenger);
    }

    @Override
    public void deletePassenger(Long id) {
        passengerDao.deletePassenger(id);
    }

    @Override
    public Page<Passenger> findAll(PageInfo pageInfo) {
        Pageable pageable = PageRequest.of(pageInfo.getPageNo(), pageInfo.getPageSize(), pageInfo.getSortOrder().equalsIgnoreCase("asc") ? Sort.by(pageInfo.getSortBy()).ascending() : Sort.by(pageInfo.getSortBy()).descending());

        return passengerDao.findAll(pageable);
    }

    @Override
    public Long getLastPassengerId() {
        return passengerDao.getLastPassengerId();
    }

    @Override
    public Passenger findPassengerByEmail(String email) {
        return passengerDao.findPassengerByEmail(email);
    }

    @Override
    public Passenger findByFirstNameAndId(String firstName, String id) {
        return passengerDao.findPassengerByIdAndFirstName(firstName, id);
    }
}
