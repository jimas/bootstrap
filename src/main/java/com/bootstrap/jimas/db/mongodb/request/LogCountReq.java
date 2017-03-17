package com.bootstrap.jimas.db.mongodb.request;

import java.util.Date;

import com.bootstrap.jimas.db.mongodb.response.BaseRes;

public class LogCountReq extends BaseRes {

    private static final long serialVersionUID = -3125759779338355280L;
    
    
    private String siteSource;
    
    private Integer days=2;//统计多少天
    
    private Date operateDate;

    public String getSiteSource() {
        return siteSource;
    }

    public void setSiteSource(String siteSource) {
        this.siteSource = siteSource;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LogCountReq [");
        if (siteSource != null) builder.append("siteSource=").append(siteSource).append(", ");
        if (days != null) builder.append("days=").append(days).append(", ");
        if (operateDate != null) builder.append("operateDate=").append(operateDate);
        builder.append("]");
        return builder.toString();
    }
    
    

}
