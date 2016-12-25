package com.bootstrap.jimas.db.mongodb.domain;

import org.springframework.data.annotation.Id;

public class CityDomain extends BaseDomain{
    
    private static final long serialVersionUID = 1393078008979732007L;

    @Id
    private String cityId;//城市id
    
    private String provice;//省名称
    
    private String cityName;//城市名称

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    
    
    
    
}
