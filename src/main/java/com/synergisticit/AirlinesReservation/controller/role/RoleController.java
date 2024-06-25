package com.synergisticit.AirlinesReservation.controller.role;

import com.synergisticit.AirlinesReservation.domain.Role;
import com.synergisticit.AirlinesReservation.service.role.RoleService;
import com.synergisticit.AirlinesReservation.service.user.UserService;
import com.synergisticit.AirlinesReservation.validation.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class RoleController {
    @Autowired private RoleService roleService;
    @Autowired private RoleValidator roleValidator;
    @RequestMapping({"/roleForm", "/roles"})
    public String roleForm(Role role, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("role", role);
        return "roleForm";
    }
    @RequestMapping("/saveRole")
    public String saveRole(@Validated @ModelAttribute Role role, Model model, BindingResult bindingResult) {
        roleValidator.validate(role, bindingResult);
        if(bindingResult.hasErrors()) {
            model.addAttribute("Error", bindingResult.getFieldError());
            model.addAttribute("role", role);
            model.addAttribute("roles", roleService.getAllRoles());
            return "roleForm";
        }else{
            Role newRole = roleService.createRole(role);
            model.addAttribute("role", newRole);
            model.addAttribute("roles", roleService.getAllRoles());
            return "redirect:roleForm";}

    }
    @RequestMapping("/updateRole")
    public String updateRole(Role role, Model model) {
        Role retrievedRole = roleService.getRoleById(role.getRoleId());
        model.addAttribute("role", retrievedRole);
        model.addAttribute("roles", roleService.getAllRoles());
        return "roleForm";
    }

}
