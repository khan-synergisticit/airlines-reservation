package com.synergisticit.AirlinesReservation.service.user;

import com.synergisticit.AirlinesReservation.dao.user.UserDao;
import com.synergisticit.AirlinesReservation.domain.PageInfo;
import com.synergisticit.AirlinesReservation.domain.Role;
import com.synergisticit.AirlinesReservation.domain.User;
import com.synergisticit.AirlinesReservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private UserDao userDao;

    private User currentUser;


    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public void delete(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public Page<User> findAll(PageInfo pageInfo) {
        Pageable pageable = PageRequest.of(pageInfo.getPageNo(), pageInfo.getPageSize(), pageInfo.getSortOrder().equalsIgnoreCase("asc") ? Sort.by(pageInfo.getSortBy()).ascending() : Sort.by(pageInfo.getSortBy()).descending());

        return userDao.getUsers(pageable);
    }

    @Override
    public List<User> findAll() {
        List<User> users = userDao.findAll();
        return users;
    }

    @Override
    public Long getCurrentUserId() {
        return userDao.getNextSeriesId();
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUserName(username);
    }

    private void initCurrentUser(){
        String user =  SecurityContextHolder.getContext().getAuthentication().getName();
        this.currentUser = userDao.findByUserName(user);
    }
    @Override
    public User getCurrentUser() {
        initCurrentUser();
        return this.currentUser;
    }
    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Boolean hasUserRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(role));
    }

    @Override
    public List<Role> getUserRoles(Long userId) {
        List<Role> roles = userDao.getRolesByUserId(userId);
        return roles;
    }

    @Override
    public List<String> getUserRolesString(Long userId) {
        List<Role> roles = userDao.getRolesByUserId(userId);
        List<String> roleStrings = new ArrayList<>();
        for (Role role : roles) {
            roleStrings.add(role.getRoleName());
        }
        return roleStrings;
    }


}
