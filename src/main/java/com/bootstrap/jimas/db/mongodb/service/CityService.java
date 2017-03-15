package com.bootstrap.jimas.db.mongodb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bootstrap.jimas.common.SpringDataPageable;
import com.bootstrap.jimas.db.mongodb.dao.CityRepository;
import com.bootstrap.jimas.db.mongodb.domain.CityDomain;

@Service
public class CityService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CityRepository cityRepository;
    /**
     * 保存city信息
     * @param city
     */
    public void save(CityDomain city){
        Assert.isNull(city);
        mongoTemplate.save(city);
    }
    /**
     * 保存list
     * @param city
     */
    public void saveList(List<CityDomain> city){
        Assert.notNull(city);
        cityRepository.save(city);
    }
    /**
     * 根据city编码查询
     * @param cityId
     * @return
     */
    public CityDomain getCityById(String cityId){
        
        return mongoTemplate.findById(cityId, CityDomain.class);
    }
    
    /**
     * 根据city名称查询
     * @param cityId
     * @return
     */
    public CityDomain getCityByName(String cityName){
        Query query=new Query();
        CriteriaDefinition criteriaDefinition=new Criteria("cityName").is(cityName);
        query.addCriteria(criteriaDefinition);
        return mongoTemplate.findOne(query, CityDomain.class);
    }

    /**
     * 根据省名称查询
     * @param cityId
     * @return
     */
    public List<CityDomain> getCitysByProvice(String provice){
        
        return cityRepository.findByProviceLike(provice);
    }
    
    public Page<CityDomain> findByPage(SpringDataPageable pageReq){
        
        Query query=new Query();
        List<Order> orders=new ArrayList<Order>();
        orders.add(new Order(Direction.ASC,"cityId"));
        pageReq.setSort(new Sort(orders));
        long count = mongoTemplate.count(query, CityDomain.class);
        List<CityDomain> list = mongoTemplate.find(query.with(pageReq), CityDomain.class);
     
        Page<CityDomain> page=new PageImpl<CityDomain>(list, pageReq, count);
        if(pageReq.getPageNumber()>page.getTotalPages()){
            pageReq.setPagenamber(page.getTotalPages());
        }
        return page;
        
    }
}
