package com.synergisticit.AirlinesReservation.controller.passenger;

import com.synergisticit.AirlinesReservation.domain.Passenger;
import com.synergisticit.AirlinesReservation.service.passenger.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passenger/rest")
public class PassengerRestController {

    @Autowired
    private PassengerService passengerService;

    @RequestMapping("/save")
    public ResponseEntity<?> createPassenger(@RequestBody Passenger passenger) {
        return new ResponseEntity<>(passengerService.savePassenger(passenger), HttpStatus.CREATED);
    }

    @RequestMapping("/update")
    public ResponseEntity<?> updatePassenger(@RequestBody Passenger passenger) {
        return new ResponseEntity<>(passengerService.updatePassenger(passenger), HttpStatus.OK);
    }

    @RequestMapping("/delete")
    public ResponseEntity<?> deletePassenger(@RequestBody Long passengerId) {
        passengerService.deletePassenger(passengerId);
        return new ResponseEntity<>("Passenger deleted.", HttpStatus.OK);
    }

    @RequestMapping("/find")
    public ResponseEntity<?> findPassenger(@RequestParam Long passengerId) {
        Passenger passenger = passengerService.getPassengerById(passengerId);
        return new ResponseEntity<>(passenger, HttpStatus.OK);
    }

    @RequestMapping("/findAll")
    public ResponseEntity<?> findAllPassenger() {
        return new ResponseEntity<>(passengerService.getAllPassengers(), HttpStatus.OK);
    }

}
