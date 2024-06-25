package com.synergisticit.AirlinesReservation.repository;

import com.synergisticit.AirlinesReservation.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String role);

}
