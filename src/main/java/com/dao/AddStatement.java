//package com.dao;
//
//import com.domain.User;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class AddStatement {
//
//    User user;
//
//    public AddStatement(User user) {
//        this.user = user;
//    }
//
//    StatementStrategy st = new StatementStrategy() {
//        @Override
//        public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
//
//            PreparedStatement ps = conn.prepareStatement("INSERT INTO users(id, name, password) VALUES (?, ?, ?)");
//            ps.setString(1, user.getId());
//            ps.setString(2, user.getName());
//            ps.setString(3, user.getPassword());
//
//            return ps;
//        }
//    };
//}