package com.bootstrap.jimas.db.mongodb.dao;
 
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bootstrap.jimas.db.mongodb.domain.UserInfo;
 
/**
 * @Description 用户dao
 * @author weqinjia.liu
 * @Date 2016年10月9日
 */
public interface  UserInfoRepository  extends MongoRepository<UserInfo, String> {
    UserInfo findByUsername(String username);
    UserInfo findByUsernameAndPassword(String username,String password);
    UserInfo findByEmail(String email);
}