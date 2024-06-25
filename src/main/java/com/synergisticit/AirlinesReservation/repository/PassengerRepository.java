package com.synergisticit.AirlinesReservation.repository;

import com.synergisticit.AirlinesReservation.domain.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Page<Passenger> findAll(Pageable pageable);

    @Query(value = "SELECT passenger_id  FROM passenger ORDER BY passenger_id  DESC LIMIT 1", nativeQuery = true)
    Long getLastPassengerId();

    Passenger findByEmail(String email);
}
