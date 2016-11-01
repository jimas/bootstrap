package com.bootstrap.jimas.db.mongodb.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bootstrap.jimas.db.mongodb.BaseTest;
import com.bootstrap.jimas.db.mongodb.domain.UserInfo;

public class UserInfoServiceTest extends BaseTest {
    String username="root";
    String password="root";

    @Autowired
    private UserInfoService userInfoService;
    
    @Test
    public void testFindByUsername() {
        UserInfo userInfo = userInfoService.findByUsername(username);
        System.out.println(userInfo);
    }

    @Test
    public void testFindByUsernameAndPassword() {
        UserInfo userInfo = userInfoService.findByUsernameAndPassword(username, password);
        System.out.println(userInfo);
    }

}
