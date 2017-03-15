package com.bootstrap.jimas.api;

import org.springframework.data.domain.Page;

import com.bootstrap.jimas.common.SpringDataPageable;
import com.bootstrap.jimas.db.mongodb.domain.LogDomain;
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
}
