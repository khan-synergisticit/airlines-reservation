package com.synergisticit.AirlinesReservation.controller.airlines;

import com.synergisticit.AirlinesReservation.domain.Airlines;
import com.synergisticit.AirlinesReservation.domain.Flight;
import com.synergisticit.AirlinesReservation.domain.PageInfo;
import com.synergisticit.AirlinesReservation.service.airlines.AirlinesService;
import com.synergisticit.AirlinesReservation.validation.AirlinesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/airlines")
public class AirlinesController {
    @Autowired
    AirlinesService airlinesService;

    @Autowired private AirlinesValidator airlinesValidator;

    long nextAirlinesId = 1;

    private final PageInfo airlinesPageInfo = new PageInfo(0, 10, "airlineName",  "desc");

    @RequestMapping({"/airlinesForm", "/"})
    public String airlinesView( @ModelAttribute Airlines airlines, Model model) {
        Page<Airlines> allAirlines = airlinesService.getAllAirlines(airlinesPageInfo);
        if(airlinesService.getAirlinesId() != null){
            nextAirlinesId = airlinesService.getAirlinesId()+1;
        }
        airlinesPageInfo.setTotalPages(allAirlines.getTotalPages());
        model.addAttribute("airlinesPageInfo", airlinesPageInfo);
        model.addAttribute("totalPages", allAirlines.getTotalPages());
        model.addAttribute("allAirlines", allAirlines.getContent());
        List<Flight> flights = new ArrayList<>();
        airlines.setFlightList(flights);
        airlines.setAirlineId(nextAirlinesId);
        model.addAttribute("airlines", airlines);
        return "airlinesForm";
    }

    @RequestMapping(value = "/setAirlinesPageNo")
    public String setAirlinesPageNo(@ModelAttribute PageInfo pageInfo, Model model){

        airlinesPageInfo.setPageNo(pageInfo.getPageNo());
        model.addAttribute("airlinesPageInfo", airlinesPageInfo);
        return "redirect:airlinesForm";
    }

    @PostMapping("/toggleAirlinesId")
    public String toggleAirlinesId() {
        airlinesPageInfo.setSortBy("airlineId");
        airlinesPageInfo.toggleSortOrder();
        return "redirect:airlinesForm";
    }
    @PostMapping("/toggleAirlinesCode")
    public String toggleAirlinesCode() {
        airlinesPageInfo.setSortBy("airlineCode");
        airlinesPageInfo.toggleSortOrder();

        return "redirect:airlinesForm";
    }
    @PostMapping("/toggleAirlinesName")
    public String toggleAirlinesName() {
        airlinesPageInfo.setSortBy("airlineName");
        airlinesPageInfo.toggleSortOrder();

        return "redirect:airlinesForm";
    }

    @RequestMapping("/deleteAirlines")
    public String deleteAirlines( Airlines airlines, Model model) {
        airlinesService.deleteAirlines(airlines);
        return "airlinesForm";
    }

    @RequestMapping("/updateAirlines")
    public String updateAirlines( Airlines airlines, Model model) {
        Airlines retrievedAirlines = airlinesService.getAirlines(airlines.getAirlineId());
        model.addAttribute("airlines", retrievedAirlines);
        model.addAttribute("airlinesPageInfo", airlinesPageInfo);
        Page<Airlines> allAirlines = airlinesService.getAllAirlines(airlinesPageInfo);
        model.addAttribute("allAirlines", allAirlines.getContent());
        return "airlinesForm";
    }

    @PostMapping("/saveAirlines")
    public String saveAirlines(@Validated @ModelAttribute Airlines airline, Model model, BindingResult bindingResult) {
        airlinesValidator.validate(airline, bindingResult);
        if(bindingResult.hasErrors()) {
            model.addAttribute("Error", bindingResult.hasErrors());
            Page<Airlines> allAirlines = airlinesService.getAllAirlines(airlinesPageInfo);
            model.addAttribute("airlinesPageInfo", airlinesPageInfo);
            model.addAttribute("totalPages", allAirlines.getTotalPages());
            model.addAttribute("allAirlines", allAirlines.getContent());
            return "airlinesForm";
        }
        else {
            Airlines airline1 = airlinesService.createAirlines(airline);
            model.addAttribute("airlines", airline1);
            return "redirect:airlinesForm";
        }

    }

}
