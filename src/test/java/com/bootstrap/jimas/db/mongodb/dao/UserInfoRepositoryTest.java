package com.bootstrap.jimas.db.mongodb.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bootstrap.jimas.db.mongodb.BaseTest;
import com.bootstrap.jimas.db.mongodb.domain.UserInfo;

public class UserInfoRepositoryTest extends BaseTest {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Test
    public void testFindByUsername() {
        String username="root";
        UserInfo userInfo = userInfoRepository.findByUsername(username);
        System.out.println(userInfo);
    }
    
    @Test
    public void testSaveUserInfo() {
        UserInfo arg0=new UserInfo();
        arg0.setName("app");
        arg0.setPassword("app");
        arg0.setUsername("app1");
        arg0.setEmail("1281429154@qq.com");
        UserInfo save = userInfoRepository.save(arg0);
        System.out.println(save);
    }
    @Test
    public void testFindByEmail() {
        String email="1281429154@qq.com";
        UserInfo save = userInfoRepository.findByEmail(email);
        System.out.println(save);
    }
    
    

}
