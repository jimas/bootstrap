package com.bootstrap.jimas.http;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.jimas.common.util.GsonUtil;

public class HttpClientUtilTest {

    String url="http://127.0.0.1:8080/gcrs-service/rest/louvre/saveCityTax";
    
    RestTemplate rt=new RestTemplate();
    @Test
    public void testSaveLouvreHotelInfo(){
        
        LouvreHotelInfoReq louvreHotelRq=new LouvreHotelInfoReq();
        louvreHotelRq.setHotelcode("BEL2");
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("hotelcode", "BEL2");
//        String doPost = HttpClientUtil.doPost(url, params,"application/json", "UTF-8");
//        System.out.println(doPost);
        
        String jsonString = GsonUtil.toJsonString(params);
//        
//        System.out.println(postForEntity.getBody());
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//        headers.setContentType(type);
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON) );
        HttpEntity<String> entity = new HttpEntity<String>(jsonString,headers);
        String result = rt.postForObject(url, entity, String.class);
        System.out.println(result);
        LouvreServiceTaxReq louvreServiceTaxReq = new LouvreServiceTaxReq();
        louvreServiceTaxReq.setAmount(2D);
        louvreServiceTaxReq.setHotelCode("BEL222");
        String postForObject = rt.postForObject(url, louvreServiceTaxReq, String.class);
        System.out.println(postForObject);
    }
}
