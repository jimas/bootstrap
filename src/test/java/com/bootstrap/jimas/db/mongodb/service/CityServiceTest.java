package com.bootstrap.jimas.db.mongodb.service;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.bootstrap.jimas.common.SpringDataPageable;
import com.bootstrap.jimas.db.mongodb.BaseTest;
import com.bootstrap.jimas.db.mongodb.domain.CityDomain;
import com.bootstrap.jimas.db.mongodb.response.CityRes;
import com.bootstrap.jimas.utils.GsonUtil;
import com.google.common.base.Charsets;
import com.google.common.reflect.TypeToken;

public class CityServiceTest extends BaseTest {

    @Autowired
    private CityService cityService;
    @SuppressWarnings("unchecked")
    @Test
    public void testSave() {
        try {
            
            String cityJson = com.google.common.io.Resources.toString(com.google.common.io.Resources.getResource("doc/city.json"), Charsets.UTF_8);
            List<CityRes> resList= (List<CityRes>) GsonUtil.parseJsonList(cityJson, new TypeToken<ArrayList<CityRes>>(){
                private static final long serialVersionUID = 6294966378044801567L;
                }.getType());
            for (CityRes cityRes : resList) {
                List<CityDomain> citys = cityRes.getCitys();
                cityService.saveList(citys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Test
    public void testGetCityById() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetCityByName() {
        CityDomain city = cityService.getCityByName("合肥");
        System.out.println(city);
    }

    @Test
    public void testGetCitysByProvice() {
        String provice="青海";
        List<CityDomain> citys = cityService.getCitysByProvice(provice);
        for (CityDomain cityDomain : citys) {
            System.out.println(cityDomain);
        }
    }

    @Test
    public void testFindByPage(){
        SpringDataPageable pageReq=new SpringDataPageable();
        pageReq.setPagenamber(49);
        Page<CityDomain> page = cityService.findByPage(pageReq);
        List<CityDomain> content = page.getContent();
        int totalPages = page.getTotalPages();
        System.out.println(totalPages);
        System.out.println(page.hasNext());
        System.out.println(page.isLast());
        System.out.println(page);
    }
}
