package com.bootstrap.jimas.db.mongodb.request;

import java.util.Date;

import com.bootstrap.jimas.db.mongodb.response.BaseRes;

public class LogCountReq extends BaseRes {

    private static final long serialVersionUID = -3125759779338355280L;
    
    
    private String siteSource;
    
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
    
    

}
