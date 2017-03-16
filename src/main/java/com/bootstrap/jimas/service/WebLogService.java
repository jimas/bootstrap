package com.bootstrap.jimas.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bootstrap.jimas.api.WebLogApi;
import com.bootstrap.jimas.common.SpringDataPageable;
import com.bootstrap.jimas.config.ParamsConfig;
import com.bootstrap.jimas.db.mongodb.dao.LogDomainRepository;
import com.bootstrap.jimas.db.mongodb.domain.CityDomain;
import com.bootstrap.jimas.db.mongodb.domain.LogDomain;
import com.bootstrap.jimas.db.mongodb.request.BaseKeyReq;
import com.bootstrap.jimas.db.mongodb.request.LogCountReq;
import com.jimas.common.ResultVo;
import com.jimas.common.util.DateUtil;
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

    @Override
    public ResultVo<Page<LogDomain>> findPagesByParams(SpringDataPageable<LogCountReq> pageReq) {
        Query query=new Query();
        List<Order> orders=new ArrayList<Order>();
        orders.add(new Order(Direction.ASC,"operateDate"));
        pageReq.setSort(new Sort(orders));
        query.with(pageReq);
        LogCountReq param = pageReq.getParam();
        if(!StringUtils.isEmpty(param)){
            Date operateDate = param.getOperateDate();
            if(!StringUtils.isEmpty(operateDate)){
                CriteriaDefinition criteriaDefinition=new Criteria("operateDate").lte(operateDate).gt(DateUtils.addDays(operateDate, -2));
                query.addCriteria(criteriaDefinition);
            }
        }
        long count = mongoTemplate.count(query, LogDomain.class);
        List<LogDomain> list = mongoTemplate.find(query, LogDomain.class);
     
        Page<LogDomain> page=new PageImpl<LogDomain>(list, pageReq, count);
        if(pageReq.getPageNumber()>page.getTotalPages()){
            pageReq.setPagenamber(page.getTotalPages());
        }
        ResultVo<Page<LogDomain>> resultVo = new ResultVo<Page<LogDomain>> ();
        resultVo.setResult(page);
        return resultVo;
    }


}
