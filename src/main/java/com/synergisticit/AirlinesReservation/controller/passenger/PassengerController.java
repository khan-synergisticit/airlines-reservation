package com.synergisticit.AirlinesReservation.controller.passenger;

import com.synergisticit.AirlinesReservation.domain.Gender;
import com.synergisticit.AirlinesReservation.domain.IdentificationType;
import com.synergisticit.AirlinesReservation.domain.PageInfo;
import com.synergisticit.AirlinesReservation.domain.Passenger;
import com.synergisticit.AirlinesReservation.service.passenger.PassengerService;
import com.synergisticit.AirlinesReservation.validation.PassengerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class PassengerController {

    @Autowired private PassengerService passengerService;
    @Autowired private PassengerValidator passengerValidator;
    long nextPassengerId = 1;

    private final PageInfo passengerPageInfo = new PageInfo(0, 10, "passengerId",  "desc");

    @RequestMapping({"/passengerForm", "/passengers"})
    public String passengerForm(@ModelAttribute Passenger passenger, Model model) {

        if(passengerService.getLastPassengerId() != null){
            nextPassengerId = passengerService.getLastPassengerId() +1;
        }
        passenger.setPassengerId(nextPassengerId);
        Page<Passenger> passengers = passengerService.findAll(passengerPageInfo);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("identificationTypes", IdentificationType.values());
        model.addAttribute("passengerPageInfo", passengerPageInfo);
        model.addAttribute("passengers", passengers.getContent());
        model.addAttribute("totalPages", passengers.getTotalPages());
        model.addAttribute("passenger", passenger);
        return "passengerForm";
    }

    @RequestMapping("/savePassenger")
    public String createPassenger(@Validated @ModelAttribute Passenger passenger, BindingResult bindingResult, Model model) {
        passengerValidator.validate(passenger, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("Errors", bindingResult.getAllErrors());
            Page<Passenger> passengers = passengerService.findAll(passengerPageInfo);
            model.addAttribute("genders", Gender.values());
            model.addAttribute("identificationTypes", IdentificationType.values());
            model.addAttribute("passengerPageInfo", passengerPageInfo);
            model.addAttribute("passengers", passengers.getContent());
            model.addAttribute("totalPages", passengers.getTotalPages());
            model.addAttribute("passenger", passenger);
            return "passengerForm";
        }
        else {
            if(passengerService.getLastPassengerId() != null){
                nextPassengerId = passengerService.getLastPassengerId() +1;
            }
            Passenger retrievedPassenger = passengerService.getPassengerById(passenger.getPassengerId());
            if(retrievedPassenger != null){
                retrievedPassenger=  passengerService.updatePassenger(passenger);
            }else{
                retrievedPassenger = passengerService.savePassenger(passenger);
            }
            model.addAttribute("passenger", retrievedPassenger);
            return "redirect:/passengerForm";
        }

    }

    @RequestMapping("/updatePassenger")
    public String updatePassenger(@ModelAttribute Passenger passenger, Model model) {
        Passenger passenger1 = passengerService.getPassengerById(passenger.getPassengerId());
        Page<Passenger> passengers = passengerService.findAll(passengerPageInfo);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("passengerGender", passenger1.getGender());
        model.addAttribute("identificationTypes", IdentificationType.values());
        model.addAttribute("passengerIdType", passenger1.getIdentificationType());
        model.addAttribute("passengerPageInfo", passengerPageInfo);
        model.addAttribute("passengers", passengers.getContent());
        model.addAttribute("passenger", passenger1);
        return "passengerForm";
    }

    @RequestMapping("/deletePassenger")
    public String deletePassenger(@ModelAttribute Passenger passenger, Model model) {
        model.addAttribute("passengerPageInfo", passengerPageInfo);
        passengerService.deletePassenger(passenger.getPassengerId());
        return "redirect:/passengerForm";
    }

    @RequestMapping("/findPassenger")
    public String findPassenger(@ModelAttribute Passenger passenger, Model model) {
       Passenger passenger1 =   passengerService.getPassengerById(passenger.getPassengerId());
        model.addAttribute("passenger", passenger1);
        return "redirect:passengerForm";
    }

    @RequestMapping(value = "/setPassengerPageNo")
    public String setPassengerPageNo(@ModelAttribute PageInfo pageInfo, Model model){
        passengerPageInfo.setPageNo(pageInfo.getPageNo());
        model.addAttribute("passengerPageInfo", passengerPageInfo);
        return "redirect:passengerForm";
    }

    @PostMapping("/togglePassengerId")
    public String toggleFlightId() {
        passengerPageInfo.setSortBy("passengerId");
        passengerPageInfo.toggleSortOrder();
        return "redirect:passengerForm";
    }

    @PostMapping("/togglePassengerName")
    public String togglePassengerName() {
        passengerPageInfo.setSortBy("lastName");
        passengerPageInfo.toggleSortOrder();

        return "redirect:passengerForm";}
}
