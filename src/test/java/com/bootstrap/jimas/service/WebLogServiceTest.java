package com.bootstrap.jimas.service;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bootstrap.jimas.api.WebLogApi;
import com.bootstrap.jimas.api.request.LogIpRq;
import com.bootstrap.jimas.api.request.LogStatisticsRq;
import com.bootstrap.jimas.api.request.LogUrlRq;
import com.bootstrap.jimas.api.response.LogStatisticsRs;
import com.bootstrap.jimas.api.response.LogUrlStatisticsRs;
import com.bootstrap.jimas.db.mongodb.BaseTest;
import com.bootstrap.jimas.db.mongodb.domain.LogIpCount;
import com.bootstrap.jimas.db.mongodb.domain.LogUrlCount;
import com.jimas.common.ResultVo;

public class WebLogServiceTest extends BaseTest {

    @Autowired
    private WebLogApi webLogService;
    
    @Test
    public void testInsertLog() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteLogBydays() {
        fail("Not yet implemented");
    }

    @Test
    public void testFindPagesByParams() {
        fail("Not yet implemented");
    }

    @Test
    public void testStatisticsSiteSourceAccess() {
        
        LogStatisticsRq logStatisticsRq=new LogStatisticsRq();
//        String siteSource="bootstrap";
//        logStatisticsRq.setSiteSource(siteSource);
        ResultVo<List<LogStatisticsRs>> rs = webLogService.statisticsSiteSourceAccess(logStatisticsRq);
        System.out.println(rs);
    }
    @Test
    public void testStatisticsLogIpCountAccess() {
        
        LogIpRq logIpRq=new LogIpRq();
//        String siteSource="bootstrap";
//        logIpRq.setSiteSource(siteSource);
        ResultVo<List<LogIpCount>> rs = webLogService.statisticsLogIpCountAccess(logIpRq);
        System.out.println(rs);
    }
    @Test
    public void testStatisticsSiteSourceUrlAccess() {
        
        LogStatisticsRq logStatisticsRq=new LogStatisticsRq();
        String siteSource="bootstrap";
        logStatisticsRq.setSiteSource(siteSource);
        ResultVo<List<LogUrlStatisticsRs>> rs = webLogService.statisticsSiteSourceUrlAccess(logStatisticsRq);
        System.out.println(rs);
    }
    @Test
    public void testStatisticsLogUrlIpCountAccess() {
        
        LogUrlRq logUrlRq=new LogUrlRq();
        ResultVo<List<LogUrlCount>> rs = webLogService.statisticsLogUrlIpCountAccess(logUrlRq);
        System.out.println(rs);
    }

}
