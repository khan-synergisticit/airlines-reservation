package com.synergisticit.AirlinesReservation.controller.role;

import com.synergisticit.AirlinesReservation.domain.Role;
import com.synergisticit.AirlinesReservation.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role/rest")
public class RoleRestController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/save")
    public ResponseEntity<?> saveRole(@RequestBody Role role) {
        return new ResponseEntity<Role>(roleService.createRole(role), HttpStatus.CREATED);
    }

    @RequestMapping("/update")
    public ResponseEntity<?> update(@RequestBody Role role) {
        return new ResponseEntity<>(roleService.updateRole(role), HttpStatus.CREATED);
    }

    @RequestMapping("/find")
    public ResponseEntity<?> findRoleByName(@RequestParam String name) {
        return new ResponseEntity<>(roleService.getRole(name), HttpStatus.OK);
    }

    @RequestMapping("/findAll")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }
}
