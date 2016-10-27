package com.bootstrap.jimas.db.mongodb.service;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bootstrap.jimas.db.mongodb.domain.LogDomain;
import com.mongodb.WriteResult;

@Service
public class LogService {
    private static final Logger logger=Logger.getLogger(LogService.class);
    
    @Value("${log.delete.date}")
    private Integer logDeleteDate;
    
    private static final Integer default_log_delete_day=1;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    public void delete5DaysAgo(){
        Date startDate=DateUtils.addDays(new Date(),-(StringUtils.isEmpty(logDeleteDate)?default_log_delete_day:logDeleteDate));
        Query query=new Query();
        CriteriaDefinition criteriaDefinition=new Criteria("operateDate").lte(startDate);
        query.addCriteria(criteriaDefinition);
        WriteResult writeResult = mongoTemplate.remove(query, LogDomain.class);
        logger.info("LogService.delete5DaysAgo--->"+writeResult);
    }
}
