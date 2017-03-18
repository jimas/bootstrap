package com.bootstrap.jimas.db.mongodb.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bootstrap.jimas.db.mongodb.dao.LogIpCountRepository;
import com.bootstrap.jimas.db.mongodb.domain.LogDomain;
import com.bootstrap.jimas.db.mongodb.domain.LogIpCount;
import com.bootstrap.jimas.db.mongodb.domain.LogUrlCount;
import com.jimas.common.util.DateUtil;

@Service
public class LogIpCountService {

    @Autowired
    private LogIpCountRepository logIpCountRepository;

    public void insertLogIpCountTable(List<LogDomain> logDomainList) {
        if(!CollectionUtils.isEmpty(logDomainList)){
            HashMap<String, LogIpCount> map=new HashMap<String, LogIpCount>();
            for (LogDomain logDomain : logDomainList) {
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
                String id=logIpCount.getId();
                LogIpCount findOne = logIpCountRepository.findOne(id);
                if(findOne!=null){
                    logIpCount.setAccess_count(logIpCount.getAccess_count()+findOne.getAccess_count());
                }
                logIpCountRepository.save(logIpCount);
            }
        }
        
    }
}
