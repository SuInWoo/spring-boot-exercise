package com.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UserDaoFactory {
    //조립 역할

    @Bean
    public UserDao awsUserDao() {   //날개 5개 선풍기
        UserDao userDao = new UserDao(new AWSConnectionMaker());
        return userDao;
    }


    @Bean
    public UserDao localUserDao() { //날개 4개 선풍기
        UserDao userDao = new UserDao(new LocalConnectionMaker());
        return userDao;
    }


}
