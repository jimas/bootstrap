package com.bootstrap.jimas.db.mongodb.response;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.bootstrap.jimas.db.mongodb.domain.BaseDomain;
import com.bootstrap.jimas.db.mongodb.domain.CityDomain;

public class CityRes extends BaseDomain{

    private static final long serialVersionUID = 3238143791265767117L;

    private String provice;
    private List<CityDomain> citys;
    public String getProvice() {
        return provice;
    }
    public void setProvice(String provice) {
        this.provice = provice;
    }
    public List<CityDomain> getCitys() {
        if(!CollectionUtils.isEmpty(citys)){
            for (CityDomain cityDomain : citys) {
                cityDomain.setProvice(provice);
            }
        }
        return citys;
    }
    public void setCitys(List<CityDomain> citys) {
        this.citys = citys;
    }
    
    
    
    
}
