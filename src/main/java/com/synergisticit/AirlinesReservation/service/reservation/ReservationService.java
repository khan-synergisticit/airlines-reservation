package com.synergisticit.AirlinesReservation.service.reservation;

import com.synergisticit.AirlinesReservation.domain.PageInfo;
import com.synergisticit.AirlinesReservation.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationService {
    Reservation create(Reservation reservation);
    Reservation update(Reservation reservation);
    void delete(Long reservationId);
    List<Reservation> findAll();
    Reservation findByTicketNumber(Long id);
    Page<Reservation> findAll(PageInfo pageInfo);
    Long getLastReservationId();
    Page<Reservation> findByUserId(Long userId, PageInfo pageInfo);
}
