package com.bootstrap.jimas.db.mongodb.dao;
 
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bootstrap.jimas.db.mongodb.domain.MenuDomain;
 
/**
 * @Description 菜单dao
 * @author weqinjia.liu
 * @Date 2016年10月9日
 */
public interface  MenuDomainRepository  extends MongoRepository<MenuDomain, String> {
}