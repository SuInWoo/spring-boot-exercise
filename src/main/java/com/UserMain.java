package com;

import com.dao.AWSConnectionMaker;
import com.dao.UserDao;
import com.domain.User;

import java.sql.SQLException;

public class UserMain {
    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDao(new AWSConnectionMaker());

        userDao.add(new User("1", "SuIn", "1123"));
        User user = userDao.get("1");

        System.out.printf(user.toString());
    }
}

