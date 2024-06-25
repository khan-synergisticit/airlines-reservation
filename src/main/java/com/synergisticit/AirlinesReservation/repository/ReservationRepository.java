package com.synergisticit.AirlinesReservation.repository;

import com.synergisticit.AirlinesReservation.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    //@Query(value = "SELECT ticket_number, checked_bags, checked_in  FROM reservation", nativeQuery = true)

    Page<Reservation> findAll(Pageable pageable);
    Reservation findByTicketNumber(Long id);
    Page<Reservation> findByUserUserId(Long userId, Pageable pageable);
    @Query(value = "SELECT ticket_number  FROM reservation ORDER BY ticket_number  DESC LIMIT 1", nativeQuery = true)
    Long getLastReservationId();
}
