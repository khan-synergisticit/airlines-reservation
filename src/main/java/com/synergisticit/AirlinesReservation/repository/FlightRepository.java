package com.synergisticit.AirlinesReservation.repository;

import com.synergisticit.AirlinesReservation.domain.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    Page<Flight> findAll(Pageable pageable);

   @Query(value = "SELECT flight_id, arrival_city, booked, capacity, departure_city, departure_date, CAST(departure_time as char ) AS departure_time, flight_number, ticket_price, operating_airlines_airline_id from flight", nativeQuery = true)
    List<Flight> findAll();
    @Query(value = "SELECT flight_id FROM flight ORDER BY flight_id DESC LIMIT 1", nativeQuery = true)
    Long getLastFlightId();

    Flight findByFlightNumber(String flightNum);

    Page<Flight> findByDepartureCityAndDepartureDate(String departureCity, LocalDate departureDate, Pageable pageable);

    Page<Flight> findByDepartureCityContainingIgnoreCaseAndArrivalCityContainingIgnoreCaseAndDepartureDate(String departureCity, String arrivalCity, LocalDate departureDate,Pageable pageable);
}

//#SELECT CAST(departure_time  AS char ) AS departure_time_str
//#FROM flight;
//ALTER TABLE flight MODIFY departure_time varchar(255);
//update flight set departure_time=TIME_FORMAT(departure_time, "%T");
//select TIME_FORMAT(departure_time, "%T") as departure_time from flight;
//SELECT flight_id, arrival_city, booked, capacity, departure_city, departure_date, CAST(departure_time  AS char ) AS departure_time_str, flight_number, ticket_price, operating_airlines_airline_id from flight;