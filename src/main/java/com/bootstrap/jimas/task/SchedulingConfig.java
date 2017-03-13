package com.bootstrap.jimas.task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.bootstrap.jimas.db.mongodb.service.LogService;
import com.bootstrap.jimas.db.mongodb.service.MenuDomainService;
import com.bootstrap.jimas.service.MenuResService;

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
    private MenuResService menuRsService;
    @Autowired
    private MenuDomainService menuDomainService;
    @Autowired
    private LogService logService;
    
    /**
     * 删除过期日志  只删除5天前的日志
     */
    @Scheduled(cron = "0 0/10 * * * ?") // 每10分钟执行一次
    public void schedulerLog() {
        logger.info("delete 5DaysAgo log start ");
        try {
            logService.delete5DaysAgo();
        } catch (Exception e) {
            logger.error("删除过期日志失败",e);
        }
        logger.info("delete 5DaysAgo log end ");
    }
    
    
    /*========================================== private method ========================================================*/

}