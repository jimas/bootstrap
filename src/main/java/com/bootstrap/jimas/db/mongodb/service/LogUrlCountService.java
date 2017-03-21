package com.bootstrap.jimas.db.mongodb.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bootstrap.jimas.db.mongodb.dao.LogUrlCountRepository;
import com.bootstrap.jimas.db.mongodb.domain.LogDomain;
import com.bootstrap.jimas.db.mongodb.domain.LogUrlCount;
import com.jimas.common.util.DateUtil;

@Service
public class LogUrlCountService {

    @Autowired
    private LogUrlCountRepository logUrlCountRepository;

    public void insertLogUrlCountTable(List<LogDomain> logDomainList) {
        if(!CollectionUtils.isEmpty(logDomainList)){
            HashMap<String, LogUrlCount> map=new HashMap<String, LogUrlCount>();
            for (LogDomain logDomain : logDomainList) {
                String remoteAddr = logDomain.getRemoteAddr();
                String url = getRealUrl(logDomain.getUrl());
                Date operateDate = logDomain.getOperateDate();
                String siteSource = StringUtils.isEmpty(logDomain.getSiteSource())?"bootstrap":logDomain.getSiteSource();
                String id=siteSource+"-"+url+"-"+DateUtil.formatDate(operateDate)+"-"+remoteAddr;
                if(!map.containsKey(id)){
                    LogUrlCount value=new LogUrlCount();
                    value.setAccess_count(1L);
                    value.setId(id);
                    value.setOperateDate(DateUtil.parseStrAutoToDate(DateUtil.format(operateDate,DateUtil.DATE_FORMAT)));
                    value.setRemoteAddr(remoteAddr);
                    value.setSiteSource(siteSource);
                    value.setUrl(url);
                    map.put(id, value);
                }else{
                    LogUrlCount logIpCount = map.get(id);
                    logIpCount.setAccess_count(logIpCount.getAccess_count()+1);
                }
            }
            
            for(Entry<String, LogUrlCount> entry :map.entrySet()){
                LogUrlCount logUrlCount = entry.getValue();
                String id=logUrlCount.getId();
                LogUrlCount findOne = logUrlCountRepository.findOne(id);
                if(findOne!=null){
                    logUrlCount.setAccess_count(logUrlCount.getAccess_count()+findOne.getAccess_count());
                }
                logUrlCountRepository.save(logUrlCount);
            }
        }
    }
    public void removeLog(Date startDate) {
        List<LogUrlCount> list = logUrlCountRepository.findByOperateDate(startDate);
        if(!CollectionUtils.isEmpty(list)){
            logUrlCountRepository.delete(list);
        }
    }
    /*=================================================== private method ================================================*/
    /**
     * url 去除 后边的“/”
     * @param url
     * @return
     */
    private String getRealUrl(String url) {
        if(url.endsWith("/")){
            String substring = url.substring(0, url.length()-1);
            return getRealUrl(substring);
        }
        return url;
    }

   
}
