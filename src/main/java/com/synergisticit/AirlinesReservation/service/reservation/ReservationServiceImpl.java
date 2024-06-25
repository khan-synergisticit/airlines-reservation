package com.synergisticit.AirlinesReservation.service.reservation;

import com.synergisticit.AirlinesReservation.domain.PageInfo;
import com.synergisticit.AirlinesReservation.domain.Reservation;
import com.synergisticit.AirlinesReservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired private ReservationRepository reservationRepository;

    @Override
    public Reservation create(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation update(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void delete(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public List<Reservation> findAll() {

        return reservationRepository.findAll();
    }

    @Override
    public Reservation findByTicketNumber(Long id) {
        Reservation reservation = reservationRepository.findByTicketNumber(id);
        return reservation;
    }

    @Override
    public Page<Reservation> findAll(PageInfo pageInfo) {
        Pageable pageable = PageRequest.of(pageInfo.getPageNo(), pageInfo.getPageSize(), pageInfo.getSortOrder().equalsIgnoreCase("asc") ? Sort.by(pageInfo.getSortBy()).ascending() : Sort.by(pageInfo.getSortBy()).descending());

        return reservationRepository.findAll(pageable);
    }

    @Override
    public Long getLastReservationId() {
        return reservationRepository.getLastReservationId();
    }

    @Override
    public Page<Reservation> findByUserId(Long userId, PageInfo pageInfo) {
        Pageable pageable = PageRequest.of(pageInfo.getPageNo(), pageInfo.getPageSize(), pageInfo.getSortOrder().equalsIgnoreCase("asc") ? Sort.by(pageInfo.getSortBy()).ascending() : Sort.by(pageInfo.getSortBy()).descending());
        return reservationRepository.findByUserUserId(userId, pageable);
    }
}
