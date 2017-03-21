package com.bootstrap.jimas.api.request;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 日志统计请求类
 * @author weqinjia.liu
 * @Date 2017年3月16日
 */
public class LogIpRq implements Serializable{
    
    private static final long serialVersionUID = -2632677264203690530L;

    private String siteSource;//来源系统
        
    private Integer size=10;

    public String getSiteSource() {
        return siteSource;
    }

    public void setSiteSource(String siteSource) {
        this.siteSource = siteSource;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LogIpRq [");
        if (siteSource != null) builder.append("siteSource=").append(siteSource).append(", ");
        if (size != null) builder.append("size=").append(size);
        builder.append("]");
        return builder.toString();
    }
    
    
    
    
    
    

}