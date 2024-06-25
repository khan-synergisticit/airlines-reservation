package com.synergisticit.AirlinesReservation.controller.reservation;

import com.synergisticit.AirlinesReservation.domain.*;
import com.synergisticit.AirlinesReservation.service.flight.FlightService;
import com.synergisticit.AirlinesReservation.service.passenger.PassengerService;
import com.synergisticit.AirlinesReservation.service.reservation.ReservationService;
import com.synergisticit.AirlinesReservation.service.user.UserDetailsServiceImpl;
import com.synergisticit.AirlinesReservation.service.user.UserService;
import com.synergisticit.AirlinesReservation.validation.ReservationValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired private ReservationService reservationService;
    @Autowired private FlightService flightService;
    @Autowired private PassengerService passengerService;
    @Autowired private ReservationValidator reservationValidator;
    @Autowired private UserService userService;

    long nextReservationId = 1;

    private final PageInfo reservationPageInfo = new PageInfo(0, 10, "ticketNumber",  "desc");

    @RequestMapping({"/", "/reservationForm"})
    public String reservationForm(Reservation reservation, Model model) {
        Page<Reservation> reservations;
        if(userService.hasUserRole("CUSTOMER")){
            User user = userService.getCurrentUser();
            reservations = reservationService.findByUserId(user.getUserId(), reservationPageInfo);
        }else{
            reservations = reservationService.findAll(reservationPageInfo);
        }
        if(reservationService.getLastReservationId() != null){
            nextReservationId = reservationService.getLastReservationId() +1;
        }
        reservation.setTicketNumber(nextReservationId);
        model.addAttribute("reservations", reservations.getContent());
        model.addAttribute("totalPages", reservations.getTotalPages());
        model.addAttribute("flights", reservation.getFlights());
        model.addAttribute("reservationPageInfo", reservationPageInfo);
        model.addAttribute("reservation", reservation);
        return "reservationForm";
    }

    @RequestMapping(value = "/updateReservation", method = RequestMethod.GET)
    public String updateReservation(@RequestParam Long reservationId, Model model) {
        Reservation retrievedReservation = reservationService.findByTicketNumber(reservationId);
        Page<Reservation> reservations;

        if(userService.hasUserRole("CUSTOMER")){
            User user = userService.getCurrentUser();
            reservations = reservationService.findByUserId(user.getUserId(), reservationPageInfo);
            model.addAttribute("user", user);
        }else{
            reservations = reservationService.findAll(reservationPageInfo);
        }
        model.addAttribute("passengers", retrievedReservation.getPassengers());
        model.addAttribute("flights", retrievedReservation.getFlights());
        model.addAttribute("reservations", reservations.getContent());
        model.addAttribute("reservationPageInfo", reservationPageInfo);
        model.addAttribute("reservation", retrievedReservation);

        return "reservationForm";
    }

    @RequestMapping(value = "/checkin", method = RequestMethod.GET)
    public String checkin(@RequestParam Long reservationId, Model model) {
        Reservation retrievedReservation = reservationService.findByTicketNumber(reservationId);
        Page<Reservation> reservations;

        if(userService.hasUserRole("CUSTOMER")){
            User user = userService.getCurrentUser();
            reservations = reservationService.findByUserId(user.getUserId(), reservationPageInfo);
            model.addAttribute("user", user);
        }else{
            reservations = reservationService.findAll(reservationPageInfo);
        }
        model.addAttribute("passengers", retrievedReservation.getPassengers());
        model.addAttribute("flights", retrievedReservation.getFlights());
        model.addAttribute("reservations", reservations.getContent());
        model.addAttribute("reservationPageInfo", reservationPageInfo);
        model.addAttribute("reservation", retrievedReservation);

        return "checkinForm";
    }

    @RequestMapping(value = "/saveCheckin", method = RequestMethod.POST)
    public String saveChecking(Reservation reservation, Model model) {
        Reservation updatedReservation = reservationService.update(reservation);
        model.addAttribute("reservation", updatedReservation);
        return "redirect:reservationForm";
    }

    @RequestMapping(value = "/saveReservation", method = RequestMethod.POST)
    public String saveReservation(@Validated @ModelAttribute Reservation reservation, BindingResult bindingResult, Model model) {
        reservationValidator.validate(reservation, bindingResult);
        model.addAttribute("reservationPageInfo", reservationPageInfo);
        Page<Reservation> reservations;
        if(reservation.getUser() == null){
            if(userService.hasUserRole("CUSTOMER")){
                User user = userService.getCurrentUser();
                reservation.setUser(user);
            }
        }
        if (bindingResult.hasErrors()) {
            System.out.println("Error: " + bindingResult.getAllErrors());
            if(userService.hasUserRole("CUSTOMER")){
                User user = userService.getCurrentUser();
                reservations = reservationService.findByUserId(user.getUserId(), reservationPageInfo);
                model.addAttribute("user", user);
            }else{
                reservations = reservationService.findAll(reservationPageInfo);
            }
            model.addAttribute("reservations", reservations.getContent());
            model.addAttribute("reservationPageInfo", reservationPageInfo);
            model.addAttribute("Error", bindingResult.hasErrors());
            return "reservationForm";
        } else {
            List<Flight> flightList = new ArrayList<>();
            for(Flight flight : reservation.getFlights()){
                Flight retrievedFlight = flightService.getFlight(flight.getFlightId());
                flightList.add(retrievedFlight);
            }
            reservation.setFlights(flightList);
            List<Passenger> passengerList = new ArrayList<>();
            for(Passenger p: reservation.getPassengers()){
                if(p.getFirstName() != null && p.getIdNumber() != null){
                    Passenger foundP =  passengerService.findByFirstNameAndId(p.getFirstName(), p.getIdNumber());
                    if(foundP != null){
                        passengerList.add(foundP);
                    }else {
                        Passenger saveP = passengerService.savePassenger(p);
                        passengerList.add(saveP);
                    }
                }else {
                    Passenger saveP = passengerService.savePassenger(p);
                    passengerList.add(saveP);
                }

            }

            reservation.setPassengers(passengerList);
            reservation.setCheckedIn(false);
            Reservation reservation1 = reservationService.create(reservation);
            model.addAttribute("flights", flightList);
            model.addAttribute("reservation", reservation1);

            if(reservationService.getLastReservationId() != null){
                nextReservationId = reservationService.getLastReservationId() +1;
            }
            return "redirect:reservationForm";
        }

    }

    @RequestMapping("/deleteReservation")
    public String deleteReservation(@RequestParam Long reservationId) {
        reservationService.delete(reservationId);
        return "redirect:/reservationForm";
    }

    @RequestMapping("/findReservation")
    public String find(@RequestParam Long reservationId, Model model) {
        Reservation reservation = reservationService.findByTicketNumber(reservationId);
        model.addAttribute("reservation", reservation);
        return "reservationForm";
    }


    @RequestMapping(value = "/setReservationPageNo")
    public String setPassengerPageNo(@ModelAttribute PageInfo pageInfo, Model model){

        reservationPageInfo.setPageNo(pageInfo.getPageNo());
        model.addAttribute("reservationPageInfo", reservationPageInfo);
        return "redirect:/reservationForm";
    }

    @PostMapping("/toggleTicketNumber")
    public String toggleTicketNumber() {
        reservationPageInfo.setSortBy("ticketNumber");
        reservationPageInfo.toggleSortOrder();
        return "redirect:reservationForm";
    }

    @PostMapping("/togglePassengerFlightNumber")
    public String togglePassengerFlightNumber() {
        reservationPageInfo.setSortBy("flights.flightNumber");
        reservationPageInfo.toggleSortOrder();
        return "redirect:reservationForm";
    }

    private void getRole(){
        User currentUser = userService.getCurrentUser();
        System.out.println("User: " + currentUser.getUsername());
        for(Role role : currentUser.getRoles()){
            System.out.println("Role: " + role.getRoleName());

        }
    }


}
