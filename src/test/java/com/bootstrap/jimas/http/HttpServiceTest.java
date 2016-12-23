package com.bootstrap.jimas.http;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.bootstrap.jimas.db.mongodb.BaseTest;
//http://m.weather.com.cn/mweather/101010100.shtml
public class HttpServiceTest extends BaseTest {

    @Autowired
    private HttpService service;
    @Test
    public void testGet() {
        String path="http://m.weather.com.cn/mweather/101010100.shtml";
//        String path="http://www.weather.com.cn/data/cityinfo/101010100.html";
//        String str = service.httpGet(path);
//        System.out.println(str);
        RestTemplate restTemplate = new RestTemplate();
        String str = restTemplate.getForObject(path, String.class);
        System.out.println(str);
    }

}
