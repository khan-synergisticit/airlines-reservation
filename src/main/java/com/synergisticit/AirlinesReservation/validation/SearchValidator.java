package com.synergisticit.AirlinesReservation.validation;

import com.synergisticit.AirlinesReservation.domain.Search;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Component
public class SearchValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Search.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Search search = (Search) target;

        LocalDate today = LocalDate.now();


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numTravelers",  "numTravelers.empty", " Number of travelers is required");

        if(search.getIsRoundTrip()){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arrivalDate",  "arrivalDate.empty", " Arrival date is required");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "origin",  "origin", " Departure city is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "destination",  "destination.empty", " Destination is required");
        if(search.getIsRoundTrip() && search.getArrivalDate() != null){
            if(today.isAfter(search.getArrivalDate()) ){
                errors.rejectValue("arrivalDate", "arrivalDate.after", "Your return date must be after today");
            }
        }
        if(search.getArrivalDate() != null && today.isAfter(search.getDepartureDate()) ){
            errors.rejectValue("departureDate", "departureDate.after", "Your departure date must be after today");
        }

    }
}
