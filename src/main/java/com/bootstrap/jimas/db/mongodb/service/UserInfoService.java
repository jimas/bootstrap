package com.bootstrap.jimas.db.mongodb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.bootstrap.jimas.db.mongodb.dao.UserInfoRepository;
import com.bootstrap.jimas.db.mongodb.domain.UserInfo;
@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public UserInfo findByUsername(String username) {
        return userInfoRepository.findByUsername(username);
    }
    /**
     * 根据用户名与密码查询
     * @param username
     * @param password
     * @return
     */
    public UserInfo findByUsernameAndPassword(String username,String password) {
        
        return userInfoRepository.findByUsernameAndPassword(username,password);
    }

    /**
     * 新增or修改
     * @param userInfo
     * @return
     */
    public UserInfo saveUserInfo(UserInfo userInfo){
        
        return userInfoRepository.save(userInfo);
    }
    
    public Boolean deleteUserInfo(UserInfo userInfo){
        userInfoRepository.delete(userInfo);
        return null;
        
    }
    
}
