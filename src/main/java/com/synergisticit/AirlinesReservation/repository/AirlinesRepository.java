package com.synergisticit.AirlinesReservation.repository;

import com.synergisticit.AirlinesReservation.domain.Airlines;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlinesRepository extends JpaRepository<Airlines, Long> {
    Page<Airlines> findAll(Pageable pageable);
    @Query(value = "SELECT airline_id FROM airlines ORDER BY airline_id DESC LIMIT 1", nativeQuery = true)
    Long getLastAirlineId();

    Airlines findByAirlineCode(String airlineCode);
}
