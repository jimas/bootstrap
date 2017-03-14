package com.bootstrap.jimas.api;

import com.bootstrap.jimas.common.ResultVo;
import com.bootstrap.jimas.db.mongodb.domain.LogDomain;
import com.bootstrap.jimas.db.mongodb.request.BaseKeyReq;
/**
 * @Description 日期记录api
 * @author weqinjia.liu
 * @Date 2017年3月14日
 */
public interface WebLogApi {
    
    public ResultVo<LogDomain> insertLog(LogDomain logDomain);
    
    public ResultVo<LogDomain> deleteLogBydays(BaseKeyReq<Integer> daysReq); 
}
