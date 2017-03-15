package com.bootstrap.jimas.task;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import com.bootstrap.jimas.api.WebLogApi;
import com.bootstrap.jimas.common.SpringDataPageable;
import com.bootstrap.jimas.db.mongodb.dao.LogIpCountRepository;
import com.bootstrap.jimas.db.mongodb.domain.LogDomain;
import com.bootstrap.jimas.db.mongodb.domain.LogIpCount;
import com.bootstrap.jimas.db.mongodb.request.BaseKeyReq;
import com.bootstrap.jimas.db.mongodb.request.LogCountReq;
import com.jimas.common.ResultVo;
import com.jimas.common.util.DateUtil;

/**
 * @Description 定时任务配置类
 * @author weqinjia.liu
 * @Date 2016年10月9日
 */
@Configuration
@EnableScheduling // 启用定时任务
public class SchedulingConfig {
    
    private final Logger logger = Logger.getLogger(getClass());
    @Autowired
    private WebLogApi logService;
    @Autowired
    private LogIpCountRepository logIpCountRepository;
    
    /**
     * 删除过期日志  只删除10天前的日志
     */
    @Scheduled(cron = "0 0/10 * * * ?") // 每10分钟执行一次
    public void schedulerLog() {
        logger.info("delete 5DaysAgo log start ");
        try {
            BaseKeyReq<Integer> daysReq=new BaseKeyReq<Integer>();
            logService.deleteLogBydays(daysReq);
        } catch (Exception e) {
            logger.error("删除过期日志失败",e);
        }
        logger.info("delete 5DaysAgo log end ");
    }
    @Scheduled(cron = "0 0 0 * * ?") // 每天凌晨0点执行一次
    public void logIpCount() {
        logger.info("logIpCount log start ");
        try {
            SpringDataPageable<LogCountReq> pageReq=new SpringDataPageable<LogCountReq>();
            Integer pagenamber=1;
            Integer pagesize=100;
            LogCountReq param=new LogCountReq();
            param.setOperateDate(new Date());
            pageReq.setParam(param);
            while(true){
                pageReq.setPagenamber(pagenamber);
                pageReq.setPagesize(pagesize);
                ResultVo<Page<LogDomain>> rs = logService.findPagesByParams(pageReq);
                Page<LogDomain> page = rs.getResult();
                insertLogIpCountTable(page);
                if(pagenamber>=page.getTotalPages()){
                    break;
                }
                pagenamber++;
            }
        } catch (Exception e) {
            logger.error("删除过期日志失败",e);
        }
        logger.info("logIpCount log end ");
    }
    
    
    /*========================================== private method ========================================================*/
    private void insertLogIpCountTable(Page<LogDomain> page) {
        List<LogDomain> logList = page.getContent();
        if(!CollectionUtils.isEmpty(logList)){
            HashMap<String, LogIpCount> map=new HashMap<String, LogIpCount>();
            for (LogDomain logDomain : logList) {
                String remoteAddr = logDomain.getRemoteAddr();
                Date operateDate = logDomain.getOperateDate();
                String siteSource = StringUtils.isEmpty(logDomain.getSiteSource())?"bootstrap":logDomain.getSiteSource();
                String id=siteSource+"-"+DateUtil.formatDate(operateDate)+"-"+remoteAddr;
                if(!map.containsKey(id)){
                    LogIpCount value=new LogIpCount();
                    value.setAccess_count(1L);
                    value.setId(id);
                    value.setOperateDate(DateUtil.parseStrAutoToDate(DateUtil.format(operateDate,DateUtil.DATE_FORMAT)));
                    value.setRemoteAddr(remoteAddr);
                    value.setSiteSource(siteSource);
                    map.put(id, value);
                }else{
                    LogIpCount logIpCount = map.get(id);
                    logIpCount.setAccess_count(logIpCount.getAccess_count()+1);
                }
            }
            
            for(Entry<String, LogIpCount> entry :map.entrySet()){
                LogIpCount logIpCount = entry.getValue();
                logIpCountRepository.save(logIpCount);
            }
        }
        
    }
}