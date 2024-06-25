package com.synergisticit.AirlinesReservation.validation;

import com.synergisticit.AirlinesReservation.domain.Flight;
import com.synergisticit.AirlinesReservation.domain.Passenger;
import com.synergisticit.AirlinesReservation.domain.Reservation;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ReservationValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Reservation.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Reservation reservation = (Reservation) target;
//        for(Flight flight : reservation.getFlights()) {
//            if(flight.getFlightNumber() == null){
//                //errors.reject( "Flight number is required");
//            }
//        }

        for(Passenger passenger : reservation.getPassengers()) {
            if(passenger.getPassengerId() == null){
                //errors.reject( "Passenger id is required");
            }

        }
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passenger.firstName", "First Name is Required");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors,"checkedBags", "checkedBags.empty", "Number of checked bags are Required");

    }
}
