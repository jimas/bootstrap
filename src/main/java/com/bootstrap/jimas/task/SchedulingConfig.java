package com.bootstrap.jimas.task;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.bootstrap.jimas.api.WebLogApi;
import com.bootstrap.jimas.common.SpringDataPageable;
import com.bootstrap.jimas.db.mongodb.domain.LogDomain;
import com.bootstrap.jimas.db.mongodb.request.BaseKeyReq;
import com.bootstrap.jimas.db.mongodb.request.LogCountReq;
import com.bootstrap.jimas.db.mongodb.service.LogIpCountService;
import com.bootstrap.jimas.db.mongodb.service.LogUrlCountService;
import com.jimas.common.ResultVo;

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
    private LogIpCountService logIpCountService;
    @Autowired
    private LogUrlCountService logUrlCountService;
    
    /**
     * 删除过期日志  只删除10天前的日志
     */
    @Scheduled(cron = "0 0/10 * * * ?") // 每10分钟执行一次
    public void schedulerLog() {
        logger.info("delete expire log  start ");
        Long startTime=System.currentTimeMillis();
        try {
            BaseKeyReq<Integer> daysReq=new BaseKeyReq<Integer>();
            logService.deleteLogBydays(daysReq);
        } catch (Exception e) {
            logger.error("删除过期日志失败",e);
        }
        Long second=(System.currentTimeMillis()-startTime)/1000;
        logger.info("delete expire log 耗时 ："+second/3600+"h "+(second/60)%60+"m "+second%60+"s");
    }
    @Scheduled(cron = "0 0 0 * * ?") // 每天凌晨0点执行一次
    public void logIpCount() {
        logger.info("logIpCount log start ");
        Long startTime=System.currentTimeMillis();
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
                List<LogDomain> logDomainList = page.getContent();
                logIpCountService.insertLogIpCountTable(logDomainList);
                if(pagenamber>=page.getTotalPages()){
                    break;
                }
                pagenamber++;
            }
        } catch (Exception e) {
            logger.error("logIpCount 统计日志失败",e);
        }
        Long second=(System.currentTimeMillis()-startTime)/1000;
        logger.info("logIpCount log 耗时 ："+second/3600+"h "+(second/60)%60+"m "+second%60+"s");
    }
    
    @Scheduled(cron = "0 0 1 * * ?") // 每天凌晨1点执行一次
    public void logUrlCount() {
        logger.info("logUrlCount log start ");
        Long startTime=System.currentTimeMillis();
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
                List<LogDomain> logDomainList = page.getContent();
                logUrlCountService.insertLogUrlCountTable(logDomainList);
                if(pagenamber>=page.getTotalPages()){
                    break;
                }
                pagenamber++;
            }
        } catch (Exception e) {
            logger.error("logUrlCount 统计日志失败",e);
        }
        Long second=(System.currentTimeMillis()-startTime)/1000;
        logger.info("logUrlCount log 耗时 ："+second/3600+"h "+(second/60)%60+"m "+second%60+"s");
    }
   
}