package com.synergisticit.AirlinesReservation.dao.role;

import com.synergisticit.AirlinesReservation.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleDao {
    Role save(Role role);
    Role update(Role role);
    Role findById(Long id);
    List<Role> findAll();
    boolean delete(Long id);
    Long getNextRoleId();
    Page<Role> getRoles(Pageable pageable);
    List<Role> getRolesByUserId(Long userId);
    Role findByRoleName(String roleName);
}
