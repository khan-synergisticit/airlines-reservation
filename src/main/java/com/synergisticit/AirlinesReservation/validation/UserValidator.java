package com.synergisticit.AirlinesReservation.validation;

import com.synergisticit.AirlinesReservation.domain.User;
import com.synergisticit.AirlinesReservation.repository.UserRepository;
import com.synergisticit.AirlinesReservation.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired private UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "required", "Username is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required", "Password is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required", "Email is required");

//        User checkUserName = userService.findByUsername(user.getUsername());
//        User checkUserEmail = userService.findByEmail(user.getEmail());
//        if(checkUserEmail != null){
//            errors.rejectValue("email", "emailTaken", "Email already taken");
//        }
//        if(checkUserName != null) {
//            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "exists","Username already exists");
//        }
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "password.mismatch", "Passwords do not match");
        }

    }
}
