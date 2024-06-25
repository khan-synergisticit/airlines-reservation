package com.synergisticit.AirlinesReservation.service.airlines;

import com.synergisticit.AirlinesReservation.domain.Airlines;
import com.synergisticit.AirlinesReservation.domain.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;


public interface AirlinesService {
    Airlines createAirlines(Airlines airlines);
    Airlines updateAirlines(Airlines airlines);
    void deleteAirlines(Airlines airlines);
    Airlines getAirlines(Long airlineId);
    Page<Airlines> getAllAirlines(PageInfo pageInfo);
    List<Airlines> getAllAirlines();
    Long getAirlinesId();
    Airlines getAirlinesByCode(String code);
}
