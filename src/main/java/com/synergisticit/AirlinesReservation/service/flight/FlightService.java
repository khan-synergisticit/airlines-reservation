package com.synergisticit.AirlinesReservation.service.flight;

import com.synergisticit.AirlinesReservation.domain.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    Flight createFlight(Flight flight);
    Flight updateFlight(Flight flight);
    Flight getFlight(Long flightId);
    List<Flight> getFlights();
    void deleteFlight(Long flightId);
    Page<Flight> getAllFlights(PageInfo pageInfo);
    Long getFlightId();
    Flight findByFlightNumber(String flightNum);
    Page<Flight> findByCityAndDate(String departureCity, LocalDate departureDate, PageInfo pageInfo);
    List<Page<Flight>> travelQuery(Search search, PageInfo pageInfo);
}
