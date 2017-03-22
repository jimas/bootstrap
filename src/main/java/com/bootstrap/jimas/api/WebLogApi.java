package com.bootstrap.jimas.api;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bootstrap.jimas.api.request.LogIpRq;
import com.bootstrap.jimas.api.request.LogStatisticsRq;
import com.bootstrap.jimas.api.request.LogUrlRq;
import com.bootstrap.jimas.api.response.LogStatisticsRs;
import com.bootstrap.jimas.api.response.LogUrlStatisticsRs;
import com.bootstrap.jimas.common.SpringDataPageable;
import com.bootstrap.jimas.db.mongodb.domain.LogDomain;
import com.bootstrap.jimas.db.mongodb.domain.LogIpCount;
import com.bootstrap.jimas.db.mongodb.domain.LogUrlCount;
import com.bootstrap.jimas.db.mongodb.request.BaseKeyReq;
import com.bootstrap.jimas.db.mongodb.request.LogCountReq;
import com.jimas.common.ResultVo;
/**
 * @Description 日期记录api
 * @author weqinjia.liu
 * @Date 2017年3月14日
 */
public interface WebLogApi {
    
    public ResultVo<LogDomain> insertLog(LogDomain logDomain);
    
    public ResultVo<String> deleteLogBydays(BaseKeyReq<Integer> daysReq); 
    
    public ResultVo<Page<LogDomain>> findPagesByParams(SpringDataPageable<LogCountReq> pageReq);
    /**
     * 统计系统 每日访问量  默认今天
     * @param logStatisticsRq
     * @return
     */
    public ResultVo<List<LogStatisticsRs>> statisticsDayAccess(LogStatisticsRq logStatisticsRq);
    /**
     * 统计系统 ip访问量
     * @param logStatisticsRq
     * @return
     */
    public ResultVo<List<LogStatisticsRs>> statisticsSiteSourceAccess(LogStatisticsRq logStatisticsRq);
    
    /**
     * 统计系统前十 ip访问量
     * @param logIpRq
     * @return
     */
    public ResultVo<List<LogIpCount>> statisticsLogIpCountAccess(LogIpRq logIpRq);
    
    /**
     * 统计系统url ip访问量
     * @param logStatisticsRq
     * @return
     */
    public ResultVo<List<LogUrlStatisticsRs>> statisticsSiteSourceUrlAccess(LogStatisticsRq logStatisticsRq);
    /**
     * 统计系统前十 url ip访问量
     * @param logIpRq
     * @return
     */
    public ResultVo<List<LogUrlCount>> statisticsLogUrlIpCountAccess(LogUrlRq logUrlRq);
}
