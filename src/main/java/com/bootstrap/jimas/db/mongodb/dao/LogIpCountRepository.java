package com.bootstrap.jimas.db.mongodb.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bootstrap.jimas.db.mongodb.domain.LogIpCount;
/**
 * @Description 我需要看出有哪些ip访问我，每个ip访问多少次
 * @author weqinjia.liu
 * @Date 2017年3月15日
 */
public interface LogIpCountRepository extends MongoRepository<LogIpCount, String> {

}
