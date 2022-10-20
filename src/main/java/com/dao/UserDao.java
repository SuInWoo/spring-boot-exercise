package com.dao;

import com.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private ConnectionMaker connectionMaker;

    public UserDao(){
        this.connectionMaker = new LocalConnectionMaker();
    }

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws SQLException {
        Connection conn = connectionMaker.getConnection();

        PreparedStatement ps = conn.prepareStatement("INSERT INTO users(id, name, password) VALUES (?, ?, ?)");
        ps.setString(1, "1");
        ps.setString(2, "SuIn");
        ps.setString(3, "1026");

        ps.executeUpdate(); //ctrl + enter
        ps.close();
        conn.close();

    }

    public User get(String id) throws SQLException {
        Connection conn = connectionMaker.getConnection();  //db연결

        PreparedStatement ps = conn.prepareStatement("SELECT id, name, password FROM `users` WHERE id = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User(rs.getString("id"), rs.getString("name"),
                rs.getString("password"));

        rs.close();
        ps.close();
        conn.close();

        return user;
    }
}
