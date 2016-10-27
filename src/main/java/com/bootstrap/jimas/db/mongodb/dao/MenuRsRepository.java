package com.bootstrap.jimas.db.mongodb.dao;
 
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bootstrap.jimas.db.mongodb.response.MenuRs;
 
/**
 * @Description 菜单Rs dao
 * @author weqinjia.liu
 * @Date 2016年10月9日
 */
public interface  MenuRsRepository  extends MongoRepository<MenuRs, String> {
    MenuRs findByMenuCode(String menuCode);
}