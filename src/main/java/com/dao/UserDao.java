package com.dao;

import com.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
       this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void deleteAll() throws SQLException {
        this.jdbcTemplate.update("delete from users");
   }

    public void add(final User user) throws SQLException {
        this.jdbcTemplate.update
                ("INSERT INTO users(id, name, password) VALUES (?, ?, ?)",
                        user.getId(), user.getName(), user.getPassword());
    }

    public int getCount() throws SQLException {
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    public User findById(String id) throws SQLException {
        String sql = "select * from users where id = ?";
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getString("id"), rs.getString("name")
                        , rs.getString("password"));
                return user;
            }
        };

        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}

