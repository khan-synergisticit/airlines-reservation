package com.synergisticit.AirlinesReservation.dao.user;

import com.synergisticit.AirlinesReservation.domain.Role;
import com.synergisticit.AirlinesReservation.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository(value = "user")
public class UserDaoImpl implements UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User save(User user) {
        String sql = "insert into user(user_id, email, password, username) values(?,?,?,?)";
        int row = jdbcTemplate.update(sql, new Object[]{user.getUserId(), user.getEmail(), user.getPassword(), user.getUsername()});
        if(row > 0) {
            User newUser = findByUserName(user.getUsername());
            if(newUser != null) {
                String sql2 = "insert into user_role(user_id, role_id) values(?,?)";
                int res = jdbcTemplate.update(sql2, new Object[]{newUser.getUserId(), 5});
                if(res > 0) {
                    return newUser;
                }
            }
            return user;
        }

        return null;
    }

    @Override
    public User update(User user) {
        String sql = "update user set username = ?, email = ?, password = ? where user_id = ?";
        int row = jdbcTemplate.update(sql, new Object[]{user.getUsername(), user.getEmail(), user.getPassword(), user.getUserId()});
        if(row > 0) {
            return user;
        }
        System.out.println("User not updated");
        return null;
    }

    @Override
    public User findById(Long id) {
        String sql = "select * from user where user_id = ?";

        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(rs.getLong(1), rs.getString(4), rs.getString(3),  rs.getString(2));
            }
        };
        User user = jdbcTemplate.queryForObject(sql, rowMapper, new Object[]{id});
        String sql1 = "select * from role where role_id in (select role_id from user_role where user_id = ?)";
        List<Role> retrievedRoles = jdbcTemplate.query(sql1, new RowMapper<Role>() {

            @Override
            public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Role(rs.getLong(1), rs.getString(2));
            }
        }, new Object[]{user.getUserId()});
        user.setRoles(retrievedRoles);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from user where user_id = ?";
        int row = jdbcTemplate.update(sql, new Object[]{id});
        if(row > 0){
            System.out.println("User deleted");
        } else{
            System.out.println("User not deleted");
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from user";
        return jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(rs.getLong(1), rs.getString(4), rs.getString(3),  rs.getString(2));
            }
        });
    }

    @Override
    public User findByUserName(String userName) {
        String sql = "select * from user where username = ?";
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(rs.getLong(1), rs.getString(4), rs.getString(3),  rs.getString(2));
            }
        };
        User user = jdbcTemplate.queryForObject(sql, rowMapper, new Object[]{userName});
        return user;
    }

    @Override
    public User findByEmail(String email) {
        String sql = "select * from user where email = ?";
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(rs.getLong(1), rs.getString(4), rs.getString(3),  rs.getString(2));
            }
        };
        User user = jdbcTemplate.queryForObject(sql, rowMapper, new Object[]{email});
        String sql1 = "select * from role where role_id in (select role_id from user_role where user_id = ?)";
        List<Role> retrievedRoles = jdbcTemplate.query(sql1, new RowMapper<Role>() {

            @Override
            public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Role(rs.getLong(1), rs.getString(2));
            }
        }, new Object[]{user.getUserId()});
        user.setRoles(retrievedRoles);
        return user;
    }

    @Override
    public Boolean existsById(Long id) {
        return findById(id) != null;
    }

    @Override
    public Long getNextSeriesId() {
        String sql = "SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1";
        Long id = jdbcTemplate.queryForObject(sql, Long.class);
        return id;
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        String numRows = "select count(*) from user";
        int totalRows = jdbcTemplate.queryForObject(numRows, Integer.class);
        String strNew = String.valueOf(pageable.getSort()).replace(":", "");
        String sql = "select * from user order by "+strNew+" limit "+pageable.getPageSize()+" offset "+pageable.getOffset();
        List<User> users = jdbcTemplate.query(sql, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(rs.getLong(1), rs.getString(4), rs.getString(3),  rs.getString(2));
            }
        });

        for(User user : users) {
            sql = "select * from role where role_id in (select role_id from user_role where user_id = ?)";
            List<Role> retrievedRoles = jdbcTemplate.query(sql, new RowMapper<Role>() {

                @Override
                public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new Role(rs.getLong(1), rs.getString(2));
                }
            }, new Object[]{user.getUserId()});
            user.setRoles(retrievedRoles);
        }
        return new PageImpl<>(users, pageable, totalRows);
    }

    @Override
    public List<Role> getRolesByUserId(Long userId) {
        String sql = "select role_id from user_role where user_id = ?";
        List<Long> roleIds = jdbcTemplate.queryForList(sql, Long.class, userId);
        List<Role> roles = new ArrayList<>();
        String roleSql = "select * from role where role_id = ?";
        RowMapper<Role> rowMapper = new RowMapper<Role>() {
            @Override
            public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Role(rs.getLong(1), rs.getString(2));
            }
        };
        for(Long roleId : roleIds) {
            Role role = jdbcTemplate.queryForObject(roleSql, rowMapper, new Object[]{roleId});
            roles.add(role);
        }


        return roles;
    }
}
