package com.bootstrap.jimas.db.mongodb.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bootstrap.jimas.db.mongodb.domain.LogUrlCount;
/**
 * @Description  访问的 url 次数
 * @author weqinjia.liu
 * @Date 2017年3月15日
 */
public interface LogUrlCountRepository extends MongoRepository<LogUrlCount, String> {

    List<LogUrlCount> findByOperateDate(Date startDate);

}
