package com.synergisticit.AirlinesReservation.controller.travel;
import com.synergisticit.AirlinesReservation.controller.reservation.ReservationController;
import com.synergisticit.AirlinesReservation.dao.user.UserDaoImpl;
import com.synergisticit.AirlinesReservation.domain.*;
import com.synergisticit.AirlinesReservation.repository.ReservationRepository;
import com.synergisticit.AirlinesReservation.service.airlines.AirlinesService;
import com.synergisticit.AirlinesReservation.service.flight.FlightService;
import com.synergisticit.AirlinesReservation.service.passenger.PassengerService;
import com.synergisticit.AirlinesReservation.service.reservation.ReservationService;
import com.synergisticit.AirlinesReservation.service.user.UserService;
import com.synergisticit.AirlinesReservation.validation.ReservationValidator;
import com.synergisticit.AirlinesReservation.validation.SearchValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/travel")
@SessionAttributes({"search", "reservation"})
public class TravelController {
    @Autowired
    private SearchValidator searchValidator;
    @Autowired private FlightService flightService;
    @Autowired private UserService userService;
    @Autowired private PassengerService passengerService;
    @Autowired private ReservationService reservationService;
    @Autowired private ReservationValidator reservationValidator;
    @Autowired private ReservationController reservationController;
    private PageInfo travelPageInfo = new PageInfo(0, 5, "departureCity",  "desc");
    @Autowired
    private UserDaoImpl user;

    @ModelAttribute("search")
    public Search getSearch() {
        return new Search();
    }
    @ModelAttribute("reservation")
    public Reservation getReservation() {

        if(!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")){
            User user = userService.getCurrentUser();
            Reservation reservation = new Reservation();
            reservation.setUser(user);
            return reservation;
        }

            return new Reservation();


    }

    @RequestMapping({"/travelForm", "/"})
    public String travelForm(@ModelAttribute Search search, @ModelAttribute Flight flight, Model model, HttpServletRequest request){

        model.addAttribute("travelPageInfo", travelPageInfo);
        List<Page<Flight>> flights = flightService.travelQuery(search, travelPageInfo);
        Page<Flight> departureFlights = flights.get(0);
        LocalDate localDate = LocalDate.now();
        search.setDepartureDate(localDate);
        model.addAttribute("departureFlights", departureFlights.getContent());
        model.addAttribute("departureTotalPages", departureFlights.getTotalPages());
        if(search.getIsRoundTrip()){
            Page<Flight> arrivalFlights = flights.get(1);
            model.addAttribute("arrivalFlights", arrivalFlights.getContent());
            model.addAttribute("arrivalTotalPages", arrivalFlights.getTotalPages());
        }
        model.addAttribute("flight", flight);
        model.addAttribute("search", search);
        //this.search = search;
        return "travelForm";
    }


    @RequestMapping(value = "/searchTravel", method = {RequestMethod.GET, RequestMethod.POST})
    public String searchTravel(@Validated @ModelAttribute Search search,  Model model, BindingResult bindingResult){

        searchValidator.validate(search, bindingResult);
        model.addAttribute("travelPageInfo", travelPageInfo);
        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "travelForm";
        } else {
            List<Page<Flight>> flights = flightService.travelQuery(search, travelPageInfo);
            Page<Flight> departureFlights = flights.get(0);
            model.addAttribute("departureFlights", departureFlights.getContent());
            model.addAttribute("departureTotalPages", departureFlights.getTotalPages());

            if(search.getIsRoundTrip()){
                Page<Flight> arrivalFlights = flights.get(1);
                model.addAttribute("arrivalFlights", arrivalFlights.getContent());
                model.addAttribute("arrivalTotalPages", arrivalFlights.getTotalPages());
            }
            model.addAttribute("numTravelers", search.getNumTravelers());
            model.addAttribute("search", search);
            return "travelForm";
        }

    }

    @RequestMapping(value = "/saveDepartureFlight")
    public ResponseEntity<?> saveDepartureFlight(@ModelAttribute Search search){
        System.out.println("save dept");
        search.setDepartureFlight(search.getDepartureFlight());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/saveArrivalFlight")
    public ResponseEntity<?> saveArrivalFlight(@ModelAttribute Search search){
        search.setArrivalFlight(search.getArrivalFlight());
        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/bookTravel", method = {RequestMethod.GET, RequestMethod.POST})
    public String bookTravel(@ModelAttribute Search search, @ModelAttribute Reservation reservation, @ModelAttribute Passenger passenger, Model model){

        List<Flight> flights = new ArrayList<>();
        Double flightCost = 0.0;
        if(search.getDepartureFlight() != null){
            Flight departureFlight1 = flightService.getFlight(search.getDepartureFlight());
            flightCost += departureFlight1.getTicketPrice();
            flights.add(departureFlight1);
        }

        if(search.getArrivalFlight() != null){
            Flight arrivalFlight = flightService.getFlight(search.getArrivalFlight());
            flightCost += arrivalFlight.getTicketPrice();
            flights.add(arrivalFlight);
        }
        reservation.setFlights(flights);
        List<Passenger> newPassengers = new ArrayList<>();
        for(int i=0; i<search.getNumTravelers(); i++){
            Passenger passenger1 = new Passenger();
            newPassengers.add(passenger1);

        }
        flightCost = flightCost*search.getNumTravelers();
        reservation.setPassengers(newPassengers);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("identificationTypes", IdentificationType.values());
        model.addAttribute("reservation", reservation);
        model.addAttribute("flightCost", flightCost);
        model.addAttribute("passengers", reservation.getPassengers());
        model.addAttribute("passenger", passenger);
        model.addAttribute("flights", reservation.getFlights());
        model.addAttribute("search", search);

        return "bookTravel";
    }

    @RequestMapping(value = "/saveTravel", method = RequestMethod.POST)
    public String saveTravel(@ModelAttribute Search search, @ModelAttribute Reservation reservation, BindingResult bindingResult, Model model){
        reservationController.saveReservation(reservation, bindingResult, model);

//        List<Flight> flightList = new ArrayList<>();
//        for(Flight flight : reservation.getFlights()){
//            Flight retrievedFlight = flightService.getFlight(flight.getFlightId());
//            flightList.add(retrievedFlight);
//        }
//        reservation.setFlights(flightList);
//        List<Passenger> passengerList = new ArrayList<>();
//        for(Passenger passenger : reservation.getPassengers()){
//            Passenger passenger1 = passengerService.findPassengerByEmail(passenger.getEmail());
//            if(passenger1 != null){
//                Passenger newPassenger = passengerService.savePassenger(passenger);
//                passengerList.add(newPassenger);
//            }
//        }
//        reservation.setPassengers(passengerList);
//        reservation.setCheckedIn(false);
//        Reservation reservation1 = reservationService.create(reservation);
//        model.addAttribute("flights", flightList);
//        model.addAttribute("reservation", reservation1);



        return  "travelForm";
    }

    @RequestMapping(value = "/setTravelPageNo")
    public String setFlightPageNo(@ModelAttribute PageInfo pageInfo, Model model){

        travelPageInfo.setPageNo(pageInfo.getPageNo());
        model.addAttribute("travelPageInfo", travelPageInfo);
        return "redirect:travelForm";
    }



}
