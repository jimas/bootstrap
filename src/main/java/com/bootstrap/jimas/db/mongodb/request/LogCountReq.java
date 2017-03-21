package com.bootstrap.jimas.db.mongodb.request;

import java.util.Date;

import com.bootstrap.jimas.db.mongodb.response.BaseRes;

public class LogCountReq extends BaseRes {

    private static final long serialVersionUID = -3125759779338355280L;
    
    
    private String siteSource;
    
    private Date startDate;
    
    private Date endDate;

    public String getSiteSource() {
        return siteSource;
    }

    public void setSiteSource(String siteSource) {
        this.siteSource = siteSource;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LogCountReq [");
        if (siteSource != null) builder.append("siteSource=").append(siteSource).append(", ");
        if (startDate != null) builder.append("startDate=").append(startDate).append(", ");
        if (endDate != null) builder.append("endDate=").append(endDate);
        builder.append("]");
        return builder.toString();
    }
    

   
    

}
