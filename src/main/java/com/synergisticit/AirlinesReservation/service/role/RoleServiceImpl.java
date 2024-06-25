package com.synergisticit.AirlinesReservation.service.role;

import com.synergisticit.AirlinesReservation.dao.role.RoleDao;
import com.synergisticit.AirlinesReservation.dao.role.RoleDaoImpl;
import com.synergisticit.AirlinesReservation.domain.Role;
import com.synergisticit.AirlinesReservation.domain.User;
import com.synergisticit.AirlinesReservation.repository.RoleRepository;
import com.synergisticit.AirlinesReservation.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired private RoleRepository roleRepository;
    @Autowired private UserService userService;
    @Autowired private RoleDao roleDao;

    @Override
    public Role getRole(String roleName) {
        return roleDao.findByRoleName(roleName);
    }

    @Override
    public Role createRole(Role role) {
        return roleDao.save(role);
    }

    @Override
    public Role updateRole(Role role) {
        return roleDao.save(role);
    }

    @Override
    public void deleteRole(String roleName) {
        roleDao.delete(roleDao.findByRoleName(roleName).getRoleId());
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = roleDao.findAll();
        return roles;
    }

    @Override
    public Role getRoleById(Long id) {
        return roleDao.findById(id);
    }

}
