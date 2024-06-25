package com.synergisticit.AirlinesReservation.dao.user;

import com.synergisticit.AirlinesReservation.domain.Role;
import com.synergisticit.AirlinesReservation.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDao {
    User save(User user);
    User update(User user);
    User findById(Long id);
    void deleteById(Long id);
    List<User> findAll();
    User findByUserName(String userName);
    Boolean existsById(Long id);
    Long getNextSeriesId();
    Page<User> getUsers(Pageable pageable);
    List<Role> getRolesByUserId(Long userId);
    User findByEmail(String userName);
}
