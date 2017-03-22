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
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bootstrap.jimas.api.WebLogApi;
import com.bootstrap.jimas.api.request.LogIpRq;
import com.bootstrap.jimas.api.request.LogStatisticsRq;
import com.bootstrap.jimas.api.request.LogUrlRq;
import com.bootstrap.jimas.api.response.LogStatisticsRs;
import com.bootstrap.jimas.api.response.LogUrlStatisticsRs;
import com.bootstrap.jimas.common.SpringDataPageable;
import com.bootstrap.jimas.config.ParamsConfig;
import com.bootstrap.jimas.db.mongodb.dao.LogDomainRepository;
import com.bootstrap.jimas.db.mongodb.domain.LogDomain;
import com.bootstrap.jimas.db.mongodb.domain.LogIpCount;
import com.bootstrap.jimas.db.mongodb.domain.LogUrlCount;
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
        ResultVo<LogDomain> resultVo = new ResultVo<LogDomain>();
        logger.info(logDomain);
        try {
            LogDomain save = logRepository.save(logDomain);
            resultVo.setResult(save);
        } catch (Exception e) {
            resultVo.setStatus(400);
            resultVo.setMessage(e.getMessage());
            logger.error("WebLogService.insertLog error " + logDomain, e);
        }
        return resultVo;
    }

    @Override
    public ResultVo<String> deleteLogBydays(BaseKeyReq<Integer> daysReq) {
        int days = paramsConfig.getLogDeleteDate();
        ResultVo<String> resultVo = new ResultVo<String>();
        if (daysReq != null && daysReq.getSiteSource() != null) {
            days = daysReq.getSiteSource();
        }
        Date startDate = DateUtils.addDays(new Date(), -days);
        WriteResult writeResult = null;
        try {
            Query query = new Query();
            CriteriaDefinition criteriaDefinition = new Criteria("operateDate").lte(startDate);
            query.addCriteria(criteriaDefinition);
            writeResult = mongoTemplate.remove(query, LogDomain.class);
        } catch (Exception e) {
            resultVo.setStatus(400);
            resultVo.setMessage(e.getMessage());
            logger.error("WebLogService.deleteLogBydays error " + daysReq, e);
        }
        logger.info("LogService.delete5DaysAgo--->" + writeResult);
        return resultVo;
    }

    @Override
    public ResultVo<Page<LogDomain>> findPagesByParams(SpringDataPageable<LogCountReq> pageReq) {
        Query query = new Query();
        List<Order> orders = new ArrayList<Order>();
        orders.add(new Order(Direction.ASC, "operateDate"));
        pageReq.setSort(new Sort(orders));
        query.with(pageReq);
        LogCountReq param = pageReq.getParam();
        if (!StringUtils.isEmpty(param)) {
            Date startDate = param.getStartDate();
            Date endDate = param.getEndDate();
            Criteria dateCriteria =null;
            if (!StringUtils.isEmpty(startDate)) {
                dateCriteria = new Criteria("operateDate");
                dateCriteria.gt(startDate);
            }
            if (!StringUtils.isEmpty(endDate)) {
                if(dateCriteria==null){
                    dateCriteria = new Criteria("operateDate");
                }
                dateCriteria.lte(endDate);
                
            }
            if(dateCriteria!=null){
                query.addCriteria(dateCriteria);
            }
            if(!StringUtils.isEmpty(param.getSiteSource())) {
                CriteriaDefinition siteCriteria=Criteria.where("siteSource").is(param.getSiteSource());
                query.addCriteria(siteCriteria);
            }
        }
        long count = mongoTemplate.count(query, LogDomain.class);
        List<LogDomain> list = mongoTemplate.find(query, LogDomain.class);

        Page<LogDomain> page = new PageImpl<LogDomain>(list, pageReq, count);
        if (pageReq.getPageNumber() > page.getTotalPages()) {
            pageReq.setPagenamber(page.getTotalPages());
        }
        ResultVo<Page<LogDomain>> resultVo = new ResultVo<Page<LogDomain>>();
        resultVo.setResult(page);
        return resultVo;
    }

    @Override
    public ResultVo<List<LogStatisticsRs>> statisticsSiteSourceAccess(LogStatisticsRq logStatisticsRq) {
        ResultVo<List<LogStatisticsRs>> resultVo = new ResultVo<List<LogStatisticsRs>>();
        Criteria criteria=new Criteria();
        boolean dayBlean=false;
        if(!StringUtils.isEmpty(logStatisticsRq)){
            if(!StringUtils.isEmpty(logStatisticsRq.getSiteSource())){
                criteria.and("siteSource").is(logStatisticsRq.getSiteSource());
            }
            if (!StringUtils.isEmpty(logStatisticsRq.getStartDate())) {
                dayBlean=true;
                Criteria and = criteria.and("operateDate");
                if (!StringUtils.isEmpty(logStatisticsRq.getDays())) {
                   and.gt(DateUtil.parseStrAutoToDate(DateUtil.format(logStatisticsRq.getStartDate(), DateUtil.DATE_FORMAT)))
                   .lte(DateUtil.parseStrAutoToDate(DateUtil.format(DateUtils.addDays(logStatisticsRq.getStartDate(), logStatisticsRq.getDays()),
                            DateUtil.DATE_FORMAT)));
                } else {
                    and.is(DateUtil.parseStrAutoToDate(DateUtil.format(logStatisticsRq.getStartDate(), DateUtil.DATE_FORMAT)));
                }
            }
        }
        
        //1.the first method
//        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(criteria)
//                , Aggregation.unwind("siteSource"), Aggregation.group("siteSource")
//                .sum("access_count").as("accessCount"),Aggregation.project("accessCount").and("siteSource").previousOperation());
//        AggregationResults<LogStatisticsRs> aggregateRs = mongoTemplate.aggregate(aggregation, LogIpCount.class, LogStatisticsRs.class);
//        resultVo.setResult(aggregateRs.getMappedResults());
        

        //2.the second method
        GroupOperation group =null;
        ProjectionOperation project =null;
        if(dayBlean){
            group= Aggregation.group("siteSource","operateDate");
            project = Aggregation.project("accessCount","operateDate");
        }else{
            group= Aggregation.group("siteSource");
            project = Aggregation.project("accessCount");
        }
        TypedAggregation<LogIpCount> aggregation =Aggregation.newAggregation(LogIpCount.class,
                Aggregation.match(criteria)
                , group
              .sum("access_count").as("accessCount"),Aggregation.sort(Direction.DESC, "accessCount"),
              project.and("siteSource").previousOperation());
        AggregationResults<LogStatisticsRs> aggregateRs1 = mongoTemplate.aggregate(aggregation, LogStatisticsRs.class);
        resultVo.setResult(aggregateRs1.getMappedResults());
        return resultVo;
    }

    @Override
    public ResultVo<List<LogIpCount>> statisticsLogIpCountAccess(LogIpRq logIpRq) {
        ResultVo<List<LogIpCount>> resultVo = new ResultVo<List<LogIpCount>>();
        Query query=new Query();
        if(!StringUtils.isEmpty(logIpRq.getSiteSource())){
            CriteriaDefinition criteria=Criteria.where("siteSource").is(logIpRq.getSiteSource());
            query.addCriteria(criteria);
        }
        SpringDataPageable<LogIpCount> sort=new SpringDataPageable<LogIpCount>();
        sort.setPagesize(logIpRq.getSize());
        List<Order> orders = new ArrayList<Order>();
        orders.add(new Order(Direction.DESC, "access_count"));
        sort.setSort(new Sort(orders));
        query.with(sort);
        List<LogIpCount> list = mongoTemplate.find(query, LogIpCount.class);
        resultVo.setResult(list);
        return resultVo;
    }

    @Override
    public ResultVo<List<LogUrlCount>> statisticsLogUrlIpCountAccess(LogUrlRq logUrlRq) {
        ResultVo<List<LogUrlCount>> resultVo = new ResultVo<List<LogUrlCount>>();
        Query query=new Query();
        if(!StringUtils.isEmpty(logUrlRq.getSiteSource())){
            CriteriaDefinition criteria=Criteria.where("siteSource").is(logUrlRq.getSiteSource());
            query.addCriteria(criteria);
        }
        SpringDataPageable<LogUrlCount> sort=new SpringDataPageable<LogUrlCount>();
        sort.setPagesize(logUrlRq.getSize());
        List<Order> orders = new ArrayList<Order>();
        orders.add(new Order(Direction.DESC, "access_count"));
        sort.setSort(new Sort(orders));
        query.with(sort);
        List<LogUrlCount> list = mongoTemplate.find(query, LogUrlCount.class);
        resultVo.setResult(list);
        return resultVo;
    }

    @Override
    public ResultVo<List<LogUrlStatisticsRs>> statisticsSiteSourceUrlAccess(LogStatisticsRq logStatisticsRq) {
        ResultVo<List<LogUrlStatisticsRs>> resultVo = new ResultVo<List<LogUrlStatisticsRs>>();
        Criteria criteria=new Criteria();
        if(!StringUtils.isEmpty(logStatisticsRq)){
            if(!StringUtils.isEmpty(logStatisticsRq.getSiteSource())){
                criteria = Criteria.where("siteSource").is(logStatisticsRq.getSiteSource());
            }
            if (!StringUtils.isEmpty(logStatisticsRq.getStartDate())) {
                criteria = criteria.and("operateDate");
                if (!StringUtils.isEmpty(logStatisticsRq.getDays())) {
                    criteria.gt(DateUtil.parseStrAutoToDate(DateUtil.format(logStatisticsRq.getStartDate(), DateUtil.DATE_FORMAT)));
                    criteria.lte(DateUtil.parseStrAutoToDate(DateUtil.format(DateUtils.addDays(logStatisticsRq.getStartDate(), logStatisticsRq.getDays()),
                            DateUtil.DATE_FORMAT)));
                } else {
                    criteria.is(DateUtil.parseStrAutoToDate(DateUtil.format(logStatisticsRq.getStartDate(), DateUtil.DATE_FORMAT)));
                }
            }
        }
        //1.the first method
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(criteria),Aggregation.group("siteSource","url")
                .sum("access_count").as("accessCount"),Aggregation.sort(Direction.DESC, "accessCount")
                ,Aggregation.project("accessCount").and("siteSource").previousOperation());
        AggregationResults<LogUrlStatisticsRs> aggregateRs = mongoTemplate.aggregate(aggregation, LogUrlCount.class, LogUrlStatisticsRs.class);
        resultVo.setResult(aggregateRs.getMappedResults());
        return resultVo;
    }

    @Override
    public ResultVo<List<LogStatisticsRs>> statisticsDayAccess(LogStatisticsRq today) {
        ResultVo<List<LogStatisticsRs>> resultVo = new ResultVo<List<LogStatisticsRs>> ();
        if(StringUtils.isEmpty(today)||StringUtils.isEmpty(today.getStartDate())){//默认今天
            today = new LogStatisticsRq();
            today.setStartDate(new Date());
        }
        
        Date startDay = DateUtil.parseStrAutoToDate(DateUtil.getDateFormat(today.getStartDate()));//今天 yyyy-MM-dd 00:00:00
        Date endDate=DateUtil.parseStrAutoToDate(DateUtil.getDateFormat(DateUtils.addDays(today.getStartDate(), 1)));//昨天 yyyy-MM-dd 00:00:00
        Criteria criteria=Criteria.where("operateDate").gt(startDay).lte(endDate);
        
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(criteria),Aggregation.group("siteSource")
                .count().as("accessCount"),Aggregation.sort(Direction.DESC, "accessCount")
                ,Aggregation.project("accessCount").and("siteSource").previousOperation());
        
        AggregationResults<LogStatisticsRs> aggregateRs = mongoTemplate.aggregate(aggregation, LogDomain.class, LogStatisticsRs.class);
        resultVo.setResult(aggregateRs.getMappedResults());
        
        return resultVo;
    }

    /*
     * =============================================== private method
     * ===================================================
     */

}
