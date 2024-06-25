package com.synergisticit.AirlinesReservation.controller.flight;

import com.synergisticit.AirlinesReservation.domain.Flight;
import com.synergisticit.AirlinesReservation.domain.PageInfo;
import com.synergisticit.AirlinesReservation.service.flight.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/flights/rest")
public class FlightRestController {

    @Autowired private FlightService flightService;

    @RequestMapping("/save")
    public ResponseEntity<?> saveFlight(@RequestBody Flight flight) {
        return new ResponseEntity<>(flightService.createFlight(flight), HttpStatus.CREATED);
    }
    @RequestMapping("/update")
    public ResponseEntity<?> updateFlight(@RequestBody Flight flight) {
        return new ResponseEntity<>(flightService.updateFlight(flight), HttpStatus.OK);
    }

    @RequestMapping("/find")
    public ResponseEntity<?> findByFlightNumber(@RequestParam Long flightNumber) {
        return new ResponseEntity<>(flightService.getFlight(flightNumber), HttpStatus.OK);
    }

    @RequestMapping("/findAll")
    public ResponseEntity<?> findAll(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String sortBy, @RequestParam String direction) {
       PageInfo flightPageInfo = new PageInfo(pageNo, pageSize, sortBy,  direction);

        Page<Flight> flights = flightService.getAllFlights(flightPageInfo);

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @RequestMapping("/delete")
    public ResponseEntity<?> deleteFlight(@RequestParam Long flightNumber) {
        flightService.deleteFlight(flightNumber);
        return new ResponseEntity<>("Flight deleted", HttpStatus.OK    );
    }
}
