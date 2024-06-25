package com.synergisticit.AirlinesReservation.controller.user;

import com.synergisticit.AirlinesReservation.domain.Role;
import com.synergisticit.AirlinesReservation.domain.User;
import com.synergisticit.AirlinesReservation.service.role.RoleService;
import com.synergisticit.AirlinesReservation.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user/rest")
public class UserRestController {
    @Autowired private UserService userService;
    @Autowired private RoleService roleService;

    @RequestMapping("/save")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        List<Role> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(roleService.getRoleById(role.getRoleId()));
        }
        user.setRoles(roles);
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @RequestMapping("/update")
    public ResponseEntity<?> update(@RequestBody User user) {
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

    @RequestMapping("/find")
    public ResponseEntity<?> find(@RequestParam Long user) {
        return new ResponseEntity<>(userService.findById(user), HttpStatus.OK);
    }

    @RequestMapping("/findAll")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

}
