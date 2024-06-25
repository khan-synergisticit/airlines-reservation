package com.synergisticit.AirlinesReservation.controller.reservation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.synergisticit.AirlinesReservation.domain.Flight;
import com.synergisticit.AirlinesReservation.domain.Passenger;
import com.synergisticit.AirlinesReservation.domain.Reservation;
import com.synergisticit.AirlinesReservation.domain.User;
import com.synergisticit.AirlinesReservation.service.airlines.AirlinesService;
import com.synergisticit.AirlinesReservation.service.flight.FlightService;
import com.synergisticit.AirlinesReservation.service.passenger.PassengerService;
import com.synergisticit.AirlinesReservation.service.reservation.ReservationService;
import com.synergisticit.AirlinesReservation.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservation/rest")
public class ReservationRestController {
    @Autowired private ReservationService reservationService;
    @Autowired private FlightService flightService;
    @Autowired
    PassengerService passengerService;
    @Autowired private UserService userService;

    @RequestMapping("/save")
    public ResponseEntity<?> save(@RequestBody JsonNode node) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        System.out.println("Saving reservation: " + node.toString());
        JsonNode flightNode = node.get("flights");
        JsonNode passengerNode = node.get("passengers");
        JsonNode userNode = node.get("user");


        User user = userService.findByUsername(userNode.get("username").asText());
        List<Flight> flightList = new ArrayList<>();
        ObjectReader reader = mapper.readerFor(new TypeReference<List<Flight>>() {
        });
        List<Flight> flights = reader.readValue(flightNode);
        for(Flight flight : flights) {
            Flight f = flightService.findByFlightNumber(flight.getFlightNumber());
            flightList.add(f);
        }
        List<Passenger> passengerList = new ArrayList<>();
        ObjectReader reader1 = mapper.readerFor(new TypeReference<List<Passenger>>() {});
        List<Passenger> passengers = reader1.readValue(passengerNode);
        for(Passenger p: passengers){
            if(p.getFirstName() != null && p.getIdNumber() != null){
                Passenger foundP =  passengerService.findByFirstNameAndId(p.getFirstName(), p.getIdNumber());
                if(foundP != null){
                    passengerList.add(foundP);
                }else {
                    Passenger saveP = passengerService.savePassenger(p);
                    passengerList.add(saveP);
                }
            }else {
                Passenger saveP = passengerService.savePassenger(p);
                passengerList.add(saveP);
            }

        }
        Reservation reservation = new Reservation();
        reservation.setPassengers(passengerList);
        reservation.setFlights(flightList);
        reservation.setUser(user);
        return new ResponseEntity<>(reservationService.create(reservation), HttpStatus.CREATED);
    }

    @RequestMapping("/update")
    public ResponseEntity<?> update(@RequestBody Reservation reservation) {
        return new ResponseEntity<>(reservationService.update(reservation), HttpStatus.OK);
    }

    @RequestMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long reservationId) {
        reservationService.delete(reservationId);
        return new ResponseEntity<>("Reservation deleted", HttpStatus.OK);
    }

    @RequestMapping("/find")
    public ResponseEntity<?> find(@RequestParam Long reservationId) {
        return new ResponseEntity<>(reservationService.findByTicketNumber(reservationId), HttpStatus.OK);
    }

    @RequestMapping("/findAll")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(reservationService.findAll(), HttpStatus.OK);
    }
}
