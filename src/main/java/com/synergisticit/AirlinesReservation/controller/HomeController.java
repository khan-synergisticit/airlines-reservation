package com.synergisticit.AirlinesReservation.controller;

import com.synergisticit.AirlinesReservation.domain.Role;
import com.synergisticit.AirlinesReservation.domain.User;
import com.synergisticit.AirlinesReservation.service.airlines.AirlinesService;
import com.synergisticit.AirlinesReservation.service.role.RoleService;
import com.synergisticit.AirlinesReservation.service.user.UserService;
import com.synergisticit.AirlinesReservation.validation.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired private UserService userService;
    @Autowired private RoleService roleService;
    @Autowired private UserValidator userValidator;
    @Autowired private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping({"/", "home"})
    public String home() {
        return "home";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error,
                        @RequestParam(value="logout", required=false) String logout,
                        HttpServletRequest req, HttpServletResponse res, Model model
    ) {
        String message = null;
        if(logout != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth != null) {
                SecurityContextLogoutHandler sclh = new SecurityContextLogoutHandler();
                sclh.logout(req, res, auth);
                message="Please login with credentials.";
            }
        }

        if(error != null) {
            message="Either username or password is incorrect.";
        }
        model.addAttribute("message", message);
        return "loginForm";
    }

    @RequestMapping({"/signupForm", "/signup"})
    public String signupForm(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "signupForm";
    }

    // Placed this here to separate from the /users path.
    @RequestMapping("/saveSignup")
    public String saveSignup(@ModelAttribute User user, Model model, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.hasErrors());
            model.addAttribute("user", user);
            return "signupForm";
        } else {
            Role role = roleService.getRole("CUSTOMER");
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            user.setUserId(userService.getCurrentUserId() + 1);
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User newUser = userService.save(user);
            model.addAttribute("user", newUser);
            return "redirect:/home";
        }
    }

    @RequestMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

}
