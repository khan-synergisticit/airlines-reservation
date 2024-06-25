package com.synergisticit.AirlinesReservation.service.flight;

import com.synergisticit.AirlinesReservation.domain.Flight;
import com.synergisticit.AirlinesReservation.domain.PageInfo;
import com.synergisticit.AirlinesReservation.domain.Search;
import com.synergisticit.AirlinesReservation.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {
    @Autowired private FlightRepository flightRepository;

    @Override
    public Flight createFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public Flight updateFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public Flight getFlight(Long flightId) {
        Optional<Flight> flight = flightRepository.findById(flightId);
        return flight.orElse(null);
    }

    @Override
    public List<Flight> getFlights() {
        List<Flight> flights = flightRepository.findAll();
        return flights;
    }

    @Override
    public void deleteFlight(Long flightId) {
        flightRepository.deleteById(flightId);
    }

    @Override
    public Page<Flight> getAllFlights(PageInfo pageInfo) {
        Pageable pageable = PageRequest.of(pageInfo.getPageNo(), pageInfo.getPageSize(), pageInfo.getSortOrder().equalsIgnoreCase("asc") ? Sort.by(pageInfo.getSortBy()).ascending() : Sort.by(pageInfo.getSortBy()).descending());

        return flightRepository.findAll(pageable);
    }

    @Override
    public Long getFlightId() {
        return flightRepository.getLastFlightId();
    }

    @Override
    public Flight findByFlightNumber(String flightNum) {
        return flightRepository.findByFlightNumber(flightNum);
    }

    @Override
    public Page<Flight> findByCityAndDate(String departureCity, LocalDate departureDate, PageInfo pageInfo) {
        Pageable pageable = PageRequest.of(pageInfo.getPageNo(), pageInfo.getPageSize(), pageInfo.getSortOrder().equalsIgnoreCase("asc") ? Sort.by(pageInfo.getSortBy()).ascending() : Sort.by(pageInfo.getSortBy()).descending());

        return flightRepository.findByDepartureCityAndDepartureDate(departureCity, departureDate, pageable);
    }

    @Override
    public List<Page<Flight>> travelQuery(Search search, PageInfo pageInfo) {
        Pageable pageable = PageRequest.of(pageInfo.getPageNo(), pageInfo.getPageSize(), pageInfo.getSortOrder().equalsIgnoreCase("asc") ? Sort.by(pageInfo.getSortBy()).ascending() : Sort.by(pageInfo.getSortBy()).descending());
        List<Page<Flight>> flights = new ArrayList<>();
        Page<Flight> departure = flightRepository.findByDepartureCityContainingIgnoreCaseAndArrivalCityContainingIgnoreCaseAndDepartureDate(search.getOrigin(), search.getDestination(), search.getDepartureDate(), pageable);
        if(search.getIsRoundTrip()){
            Page<Flight> arrival = flightRepository.findByDepartureCityContainingIgnoreCaseAndArrivalCityContainingIgnoreCaseAndDepartureDate(search.getDestination(), search.getOrigin(), search.getArrivalDate(), pageable);
            flights.add(arrival);
        }

        flights.add(departure);

        return flights;
    }


}
