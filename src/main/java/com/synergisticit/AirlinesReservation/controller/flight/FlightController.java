package com.synergisticit.AirlinesReservation.controller.flight;

import com.synergisticit.AirlinesReservation.domain.Airlines;
import com.synergisticit.AirlinesReservation.domain.Flight;
import com.synergisticit.AirlinesReservation.domain.PageInfo;
import com.synergisticit.AirlinesReservation.service.airlines.AirlinesService;
import com.synergisticit.AirlinesReservation.service.flight.FlightService;
import com.synergisticit.AirlinesReservation.validation.FlightValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/flights")
public class FlightController {

    @Autowired private FlightService flightService;
    @Autowired private FlightValidator flightValidator;
    @Autowired private AirlinesService airlinesService;
    long nextFlightId = 1;

    private final PageInfo flightPageInfo = new PageInfo(0, 10, "flightId",  "desc");

    @RequestMapping({"/flightForm", "/"})
    public String flight(@ModelAttribute Flight flight, Model model) {
        if(flightService.getFlightId() != null){
            nextFlightId = flightService.getFlightId()+1;
        }
        Page<Flight> flights = flightService.getAllFlights(flightPageInfo);

        flight.setFlightId(nextFlightId);

        model.addAttribute("operatingAirlines", airlinesService.getAllAirlines());
        model.addAttribute("totalPages", flights.getTotalPages());
        model.addAttribute("flights", flights.getContent());
        model.addAttribute("flightPageInfo", flightPageInfo);
        model.addAttribute("flight", flight);
        return "flightForm";
    }

    @RequestMapping("/saveFlight")
    public String save(Flight flight, Model model, BindingResult bindingResult) {
        model.addAttribute("flightPageInfo", flightPageInfo);
        flightValidator.validate(flight, bindingResult);
        if(bindingResult.hasErrors()) {
            Page<Flight> flights = flightService.getAllFlights(flightPageInfo);
            model.addAttribute("flights", flights.getContent());
            model.addAttribute("Errors", bindingResult.hasErrors());
            return "flightForm";
        }else {
            Airlines operatingAirlines = airlinesService.getAirlinesByCode(flight.getOperatingAirlines().getAirlineCode());
            flight.setOperatingAirlines(operatingAirlines);
            Flight flight1 =  flightService.createFlight(flight);
            if(flightService.getFlightId() != null){
                nextFlightId = flightService.getFlightId()+1;
            }

            flight.setFlightId(nextFlightId);
            model.addAttribute("flight", flight1);
            return "redirect:flightForm";
        }
    }

    @RequestMapping("/updateFlight")
    public String updateFlight( Flight flight, Model model) {
        Page<Flight> flights = flightService.getAllFlights(flightPageInfo);
        model.addAttribute("flights", flights.getContent());
        Flight flight1 =  flightService.getFlight(flight.getFlightId());
        model.addAttribute("flightPageInfo", flightPageInfo);
        model.addAttribute("flight", flight1);
        return "flightForm";
    }

    @RequestMapping("/deleteFlight")
    public String deleteFlight(@ModelAttribute Flight flight, Model model) {
        model.addAttribute("flightPageInfo", flightPageInfo);
        flightService.deleteFlight(flight.getFlightId());
        return "redirect:flightForm";
    }


    @RequestMapping(value = "/setFlightsPageNo")
    public String setFlightPageNo(@ModelAttribute PageInfo pageInfo, Model model){
        System.out.println("PageInfo: " + pageInfo.getPageNo());
       flightPageInfo.setPageNo(pageInfo.getPageNo());
        model.addAttribute("flightPageInfo", flightPageInfo);
        return "redirect:flightForm";
    }

    @PostMapping("/toggleFlightId")
    public String toggleFlightId() {
       flightPageInfo.setSortBy("flightId");
        flightPageInfo.toggleSortOrder();
        return "redirect:flightForm";
    }

    @PostMapping("/toggleFlightNumber")
    public String toggleFlightCode() {
        flightPageInfo.setSortBy("flightNumber");
        flightPageInfo.toggleSortOrder();

        return "redirect:flightForm";
    }
//    @PostMapping("toggleFlightName")
//    public String toggleFlightName() {
//        flightPageInfo.setSortBy("airlineName");
//        flightPageInfo.toggleSortOrder();
//
//        return "redirect:flightForm";
//    }


}
