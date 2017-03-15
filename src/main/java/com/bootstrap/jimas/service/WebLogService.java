package com.bootstrap.jimas.service;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.bootstrap.jimas.api.WebLogApi;
import com.bootstrap.jimas.config.ParamsConfig;
import com.bootstrap.jimas.db.mongodb.dao.LogDomainRepository;
import com.bootstrap.jimas.db.mongodb.domain.LogDomain;
import com.bootstrap.jimas.db.mongodb.request.BaseKeyReq;
import com.jimas.common.ResultVo;
import com.mongodb.WriteResult;

@Service
public class WebLogService implements WebLogApi {
    private static final Logger logger = Logger.getLogger(WebLogService.class);
    @Autowired
    private LogDomainRepository logRepository;
    @Autowired
    private ParamsConfig paramsConfig;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ResultVo<LogDomain> insertLog(LogDomain logDomain) {
        ResultVo<LogDomain>  resultVo = new ResultVo<LogDomain> ();
        logger.info(logDomain);
        try {
            LogDomain save = logRepository.save(logDomain);
            resultVo.setResult(save);
        } catch (Exception e) {
            resultVo.setStatus(400);
            resultVo.setMessage(e.getMessage());
            logger.error("WebLogService.insertLog error "+logDomain, e);
        }
        return resultVo;
    }

    @Override
    public ResultVo<String> deleteLogBydays(BaseKeyReq<Integer> daysReq) {
        int days = paramsConfig.getLogDeleteDate();
        ResultVo<String> resultVo = new ResultVo<String>();
        if (daysReq != null&&daysReq.getSiteSource()!=null) {
            days = daysReq.getSiteSource();
        }
        Date startDate = DateUtils.addDays(new Date(), -days);
        WriteResult writeResult =null;
        try {
            Query query = new Query();
            CriteriaDefinition criteriaDefinition = new Criteria("operateDate").lte(startDate);
            query.addCriteria(criteriaDefinition);
            writeResult = mongoTemplate.remove(query, LogDomain.class);
        } catch (Exception e) {
            resultVo.setStatus(400);
            resultVo.setMessage(e.getMessage());
            logger.error("WebLogService.deleteLogBydays error "+daysReq, e);
        }
        logger.info("LogService.delete5DaysAgo--->" + writeResult);
        return resultVo;
    }

}
