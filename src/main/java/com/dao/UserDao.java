package com.dao;

import com.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();
            ps = stmt.makePreparedStatement(conn);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
            if (conn != null) { try { conn.close(); } catch (SQLException e) { } }
        }

    }

    public void deleteAll() throws SQLException {
        jdbcContextWithStatementStrategy(
                new StatementStrategy() {
                    @Override
                    public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
                        return conn.prepareStatement("delete from users");
                    }
                }
        );

    }

    public void add(final User user) throws SQLException {
        jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO users(id, name, password) VALUES (?, ?, ?)");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
                return ps;
            }
        });
    }


    public User findById(String id) throws SQLException {

            Connection conn = dataSource.getConnection();

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `users` WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            User user = null;

            if (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }

            rs.close();
            ps.close();
            conn.close();

            if (user == null) throw new EmptyResultDataAccessException(1);

            return user;

    }

    public int getCount() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            ps = conn.prepareStatement("select count(*) from `users`");

            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}

