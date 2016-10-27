package com.bootstrap.jimas.mongodb.dao;

import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.bootstrap.jimas.common.Constant;
import com.bootstrap.jimas.db.mongodb.BaseTest;
import com.bootstrap.jimas.db.mongodb.dao.DemoInfoRepository;
import com.bootstrap.jimas.db.mongodb.domain.DemoInfo;
public class DemoInfoRepositoryTest extends BaseTest {
    //这里的单引号不能少，否则会报错，被识别是一个对象;
    public static final String CACHE_KEY = "'demoInfo'";
    @Autowired
    private DemoInfoRepository demoInfoRepository;
    @Test
    
    public void testFindByName() {
        String name="张三";
        finaDemoByName(name);
        
    }
    private void finaDemoByName(String name) {
        DemoInfo demoInfo = demoInfoRepository.findByName(name);
        System.out.println(demoInfo);
    }
    @Test
    public void testSave() {
        DemoInfo demoInfo = new DemoInfo();
        String string = "c0507108-5a95-45f0-8bcd-2f9f9044abc6";
        demoInfo.setId(string);
        demoInfo.setName("张三");
        demoInfo.setAge(20);
        DemoInfo save = demoInfoRepository.save(demoInfo);
        System.out.println(save);
        
    }

}
