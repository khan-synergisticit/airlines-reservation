package com.synergisticit.AirlinesReservation.dao.role;

import com.synergisticit.AirlinesReservation.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository("role")
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Role save(Role role) {
        String sql = "INSERT INTO role (role_id, role_name) VALUES (:id, :name)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", role.getRoleId());
        params.addValue("name", role.getRoleName());
        int result = namedParameterJdbcTemplate.update(sql, params);
        if(result > 0){
            return role;
        }
        return null;
    }

    @Override
    public Role update(Role role) {
        return null;
    }

    @Override
    public Role findById(Long id) {
        String sql = "SELECT * FROM role WHERE role_id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new RoleMapper());
    }



    @Override
    public List<Role> findAll() {
        String sql = "SELECT * FROM role";
        return namedParameterJdbcTemplate.query(sql, new RoleMapper());
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM role WHERE role_id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        int result = namedParameterJdbcTemplate.update(sql, params);
        if(result > 0){
            System.out.println("Removed role with id: " + id);
            return true;
        }else {
            System.out.println("Failed to delete role with id: " + id);
            return false;
        }
    }

    @Override
    public Long getNextRoleId() {
        String sql = "SELECT * FROM role ORDER BY role_id DESC LIMIT :count";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("count", 1);
        Role role = namedParameterJdbcTemplate.queryForObject(sql, params, new RoleMapper());

        assert role != null;
        return role.getRoleId();
    }

    @Override
    public Page<Role> getRoles(Pageable pageable) {
        String numRows = "select count(*) from role";
        MapSqlParameterSource params = new MapSqlParameterSource();
        int totalRows = namedParameterJdbcTemplate.queryForObject(numRows, params, Integer.class);

        MapSqlParameterSource params2 = new MapSqlParameterSource();
        String strNew = String.valueOf(pageable.getSort()).replace(":", "");
        params2.addValue("sort", strNew);
        params2.addValue("pageSize", pageable.getPageSize());
        params2.addValue("pageNo", pageable.getOffset());
        String sql = "select * from role order by "+strNew+" limit "+pageable.getPageSize()+" offset " + pageable.getOffset();
        List<Role> roles = namedParameterJdbcTemplate.query(sql, params2, new RoleMapper());

        return new PageImpl<>(roles, pageable, totalRows);
    }

    @Override
    public List<Role> getRolesByUserId(Long userId) {
        return List.of();
    }

    @Override
    public Role findByRoleName(String roleName) {
        String sql = "SELECT * FROM role WHERE role_name = :roleName";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("roleName", roleName);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new RoleMapper());
    }

    class RoleMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong(1);
            String roleName = rs.getString(2);
            return new Role(id, roleName);
        }
    }
}
