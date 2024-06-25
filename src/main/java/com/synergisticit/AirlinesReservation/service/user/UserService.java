package com.synergisticit.AirlinesReservation.service.user;

import com.synergisticit.AirlinesReservation.domain.PageInfo;
import com.synergisticit.AirlinesReservation.domain.Role;
import com.synergisticit.AirlinesReservation.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User save(User user);
    User findById(Long id);
    User update(User user);
    void delete(Long id);
    Page<User> findAll(PageInfo pageInfo);
    List<User> findAll();
    User getCurrentUser();
    Long getCurrentUserId();
    User findByUsername(String username);
    User findByEmail(String email);
    Boolean hasUserRole(String role);
    List<Role> getUserRoles(Long userId);
    List<String> getUserRolesString(Long userId);
}
