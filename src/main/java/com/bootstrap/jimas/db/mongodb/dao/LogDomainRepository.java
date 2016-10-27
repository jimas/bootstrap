package com.bootstrap.jimas.db.mongodb.dao;
 
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bootstrap.jimas.db.mongodb.domain.LogDomain;
 
/**
 * @Description 日志 dao
 * @author weqinjia.liu
 * @Date 2016年10月9日
 */
public interface  LogDomainRepository  extends MongoRepository<LogDomain, String> {
    
}