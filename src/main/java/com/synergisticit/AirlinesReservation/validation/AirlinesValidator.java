package com.synergisticit.AirlinesReservation.validation;

import com.synergisticit.AirlinesReservation.domain.Airlines;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AirlinesValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Airlines.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Airlines airlines = (Airlines) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "airlineName", "airlineName.empty", "Airlines Name cannot be empty.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "airlineCode", "airlineCode.empty", "Airlines Code cannot be empty.");
    }
}
