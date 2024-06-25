package com.synergisticit.AirlinesReservation.validation;

import com.synergisticit.AirlinesReservation.domain.Passenger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PassengerValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return Passenger.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Passenger passenger = (Passenger) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "field.required", "First Name is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "field.required","Last Name is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate","field.required",  "Date of birth is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "identificationType","field.required",  "Identification is required");


    }
}
