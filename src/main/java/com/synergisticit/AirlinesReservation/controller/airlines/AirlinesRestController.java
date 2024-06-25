package com.synergisticit.AirlinesReservation.controller.airlines;

import com.synergisticit.AirlinesReservation.domain.Airlines;
import com.synergisticit.AirlinesReservation.service.airlines.AirlinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airlines/rest")
public class AirlinesRestController {
    @Autowired private AirlinesService airlinesService;

    @RequestMapping("/findAll")
    public ResponseEntity<?> getAirlines() {
        return  new ResponseEntity<>(airlinesService.getAllAirlines(), HttpStatus.OK);
    }

    @RequestMapping("/find/")
    public ResponseEntity<?> getAirlineById(@RequestParam Long id) {
        return new ResponseEntity<>(airlinesService.getAirlines(id), HttpStatus.OK);
    }

    @RequestMapping("/update")
    public ResponseEntity<?> updateAirline(@RequestBody Airlines airlines) {
        return new ResponseEntity<>(airlinesService.updateAirlines(airlines), HttpStatus.OK);
    }

    @RequestMapping("/save")
    public ResponseEntity<?> saveAirline(@RequestBody Airlines airlines) {
        return new ResponseEntity<>(airlinesService.createAirlines(airlines), HttpStatus.CREATED);
    }
}
