package com.bootstrap.jimas.db.mongodb.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bootstrap.jimas.db.mongodb.domain.CityDomain;

public interface CityRepository extends MongoRepository<CityDomain, String> {
    
    public List<CityDomain> findByProviceLike(String provice);
    
}
