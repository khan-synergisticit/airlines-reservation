package com.synergisticit.AirlinesReservation.validation;

import com.synergisticit.AirlinesReservation.domain.Flight;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class FlightValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Flight.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Flight flight = (Flight) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "flightNumber", "required", "Flight number is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departureCity", "required", "Departure City is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arrivalCity", "required", "Arrival City is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departureTime", "required", "Departure Time is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departureDate", "required", "Departure Date Time is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ticketPrice", "required", "Ticket Price is required");
    }
}
