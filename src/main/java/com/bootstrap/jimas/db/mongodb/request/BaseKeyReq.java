package com.bootstrap.jimas.db.mongodb.request;

import com.bootstrap.jimas.db.mongodb.response.BaseRes;

public class BaseKeyReq<T> extends BaseRes {

    private static final long serialVersionUID = -1411538311411618106L;
    
    private T siteSource;

    public T getSiteSource() {
        return siteSource;
    }

    public void setSiteSource(T siteSource) {
        this.siteSource = siteSource;
    }

    
    

}
