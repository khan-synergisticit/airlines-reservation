package com.synergisticit.AirlinesReservation.service.airlines;

import com.synergisticit.AirlinesReservation.domain.Airlines;
import com.synergisticit.AirlinesReservation.domain.PageInfo;
import com.synergisticit.AirlinesReservation.repository.AirlinesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirlinesServiceImpl implements AirlinesService {
    @Autowired
    AirlinesRepository airlinesRepository;
    @Override
    public Airlines createAirlines(Airlines airlines) {
        return airlinesRepository.save(airlines);
    }

    @Override
    public Airlines updateAirlines(Airlines airlines) {
        return airlinesRepository.save(airlines);
    }

    @Override
    public void deleteAirlines(Airlines airlines) {
        airlinesRepository.delete(airlines);
    }

    @Override
    public Airlines getAirlines(Long airlineId) {
        Optional<Airlines> airlines = airlinesRepository.findById(airlineId);
        return airlines.orElse(null);
    }

    @Override
    public Page<Airlines> getAllAirlines(PageInfo pageInfo) {
        Pageable pageable = PageRequest.of(pageInfo.getPageNo(), pageInfo.getPageSize(), pageInfo.getSortOrder().equalsIgnoreCase("asc") ? Sort.by(pageInfo.getSortBy()).ascending() : Sort.by(pageInfo.getSortBy()).descending());
        return airlinesRepository.findAll(pageable);
    }

    @Override
    public List<Airlines> getAllAirlines() {
        return airlinesRepository.findAll();
    }

    @Override
    public Long getAirlinesId() {
        return airlinesRepository.getLastAirlineId();
    }

    @Override
    public Airlines getAirlinesByCode(String code) {
        return airlinesRepository.findByAirlineCode(code);
    }
}
