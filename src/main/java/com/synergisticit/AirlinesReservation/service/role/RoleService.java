package com.synergisticit.AirlinesReservation.service.role;

import com.synergisticit.AirlinesReservation.domain.Role;

import java.util.List;

public interface RoleService {
    Role getRole(String roleName);
    Role createRole(Role role);
    Role updateRole(Role role);
    void deleteRole(String roleName);
    List<Role> getAllRoles();
    Role getRoleById(Long id);
}
