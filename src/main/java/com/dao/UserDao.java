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

    public void add(User user) {
        Connection conn = null;
        try {
            conn = connectionMaker.getConnection();

            PreparedStatement ps = conn.prepareStatement("INSERT INTO users(id, name, password) VALUES (?, ?, ?)");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            ps.executeUpdate(); //ctrl + enter
            ps.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    public User get(String id) {
        Connection conn = null;  //db연결
        try {
            conn = connectionMaker.getConnection();

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `users` WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            User user = null;

            if(rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }

            rs.close();
            ps.close();
            conn.close();

            if (user == null) throw new NullPointerException();

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteAll()  {
        Connection conn = null;
        try {
            conn = connectionMaker.getConnection();

            PreparedStatement ps = conn.prepareStatement("delete from `users`");

            ps.executeUpdate();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public int getCount()  {
        Connection conn = null;
        try {
            conn = connectionMaker.getConnection();

            PreparedStatement ps = conn.prepareStatement("select count(*) from `users`");

            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            rs.close();
            ps.close();
            conn.close();

            return count;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
