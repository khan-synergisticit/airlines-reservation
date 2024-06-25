package com.synergisticit.AirlinesReservation.controller.user;

import com.synergisticit.AirlinesReservation.domain.PageInfo;
import com.synergisticit.AirlinesReservation.domain.Role;
import com.synergisticit.AirlinesReservation.domain.User;
import com.synergisticit.AirlinesReservation.service.airlines.AirlinesService;
import com.synergisticit.AirlinesReservation.service.role.RoleService;
import com.synergisticit.AirlinesReservation.service.user.UserService;
import com.synergisticit.AirlinesReservation.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired private UserService userService;
    @Autowired private RoleService roleService;
    @Autowired private UserValidator userValidator;
    @Autowired private BCryptPasswordEncoder passwordEncoder;
    private final PageInfo userPageInfo = new PageInfo(0, 10, "user_id",  "desc");

    Long nextUserId = 1L;

    @RequestMapping({"/userForm", "/"})
    public String userForm(User user, Model model) {
        user = new User();
        if(userService.getCurrentUserId() != null){
            nextUserId = userService.getCurrentUserId() +1;
        }
        model = getUserPageInfo(model);


        model.addAttribute("user", user);
        return "userForm";
    }

    @RequestMapping("/saveUser")
    public String saveUser(@Validated @ModelAttribute User user, Model model, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()) {

            model = getUserPageInfo(model);
            model.addAttribute("Errors", bindingResult.hasErrors());

            return "userForm";
        }else{

            model = getUserPageInfo(model);
            User fetchedUser = userService.findById(user.getUserId());
            if(fetchedUser != null) {
                User updatedUser;
                if(fetchedUser.getPassword().equals(user.getPassword())) {

                     updatedUser = userService.update(user);
                }else{

                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                     updatedUser = userService.update(user);
                }

                model.addAttribute("user", updatedUser);

            }else{

                Role role = roleService.getRole("CUSTOMER");
                List<Role> roles = new ArrayList<>();
                roles.add(role);
                user.setRoles(roles);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                User savedUser = userService.save(user);
                model.addAttribute("user", savedUser);
            }
            System.out.println("2");
            model = getUserPageInfo(model);



            return "redirect:userForm";
        }


    }

    @RequestMapping("/deleteUser")
    public String deleteUser(@ModelAttribute User user, Model model) {
        userService.delete(user.getUserId());

        return "redirect:userForm";
    }

    @RequestMapping("/updateUser")
    public String updateUser(User user, Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        User retrievedUser = userService.findById(user.getUserId());
        retrievedUser.makeLikeRoles(roles);
        model.addAttribute("retrievedRoles", retrievedUser.getRoles());
        model.addAttribute("user", retrievedUser);
        if(userService.hasUserRole("ADMIN")){
            Page<User> users = userService.findAll(userPageInfo);
            model.addAttribute("users", users.getContent());
            model.addAttribute("totalPages", users.getTotalPages());
        }else{
            List<User> users = new ArrayList<>();
            users.add(retrievedUser);
            model.addAttribute("users", users);
            model.addAttribute("totalPages", 1);
        }
        model.addAttribute("userPageInfo", userPageInfo);
        return "userForm";
    }


    @RequestMapping(value = "/setUserPageNo")
    public String setPassengerPageNo(@ModelAttribute PageInfo pageInfo, Model model){

        userPageInfo.setPageNo(pageInfo.getPageNo());
        model.addAttribute("userPageInfo", userPageInfo);
        return "redirect:/userForm";
    }

    @PostMapping("/toggleUserId")
    public String togglePassengerId() {
        userPageInfo.setSortBy("user_id");
        userPageInfo.toggleSortOrder();
        return "redirect:/userForm";
    }

    @PostMapping("/toggleUsername")
    public String togglePassengerFlightNumber() {
        userPageInfo.setSortBy("username");
        userPageInfo.toggleSortOrder();
        return "redirect:/userForm";
    }

    private Model getUserPageInfo(Model model){
        if(userService.hasUserRole("ADMIN")){
            Page<User> users = userService.findAll(userPageInfo);

            model.addAttribute("users", users.getContent());
            model.addAttribute("totalPages", users.getTotalPages());
        }else{
            User user1 = userService.getCurrentUser();
            List<User> users = new ArrayList<>();
            users.add(user1);
            model.addAttribute("users", users);
            model.addAttribute("totalPages", 1);
        }
        model.addAttribute("userPageInfo", userPageInfo);
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("userPageInfo", userPageInfo);
        return model;
    }
}
