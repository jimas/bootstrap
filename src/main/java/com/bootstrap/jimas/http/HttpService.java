package com.bootstrap.jimas.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * @Description  in.close();作用就是将用完的连接释放，下次请求可以复用，这里特别注意的是，如果不使用in.close();
 * 而仅仅使用response.close();结果就是连接会被关闭，并且不能被复用，这样就失去了采用连接池的意义。
 *连接池释放连接的时候，并不会直接对TCP连接的状态有任何改变，只是维护了两个Set，leased和avaliabled，leased代表被占用的连接集合，
 *avaliabled代表可用的连接的集合，释放连接的时候仅仅是将连接从leased中remove掉了，并把连接放到avaliabled集合中
 * @author weqinjia.liu
 * @Date 2016年12月23日
 */


@Component
public class HttpService {

    @Autowired
    HttpConnectionManager connManager;
    
    public String httpGet(String path){
        CloseableHttpClient httpClient=connManager.getHttpClient();
        HttpGet httpget = new HttpGet(path);
        System.out.println("executing request " + httpget.getURI());  
        String result=null;        
        CloseableHttpResponse response=null;
        try {
            // 执行get请求.   
            response = httpClient.execute(httpget);
            
         // 获取响应实体    
            HttpEntity entity = response.getEntity(); 
             
            System.out.println("--------------------------------------");  
            // 打印响应状态    
            System.out.println(response.getStatusLine());  
            if (entity != null) {  
                // 打印响应内容长度    
                System.out.println("Response content length: " + entity.getContentLength());  
                // 打印响应内容    
                result = EntityUtils.toString(entity);
                System.out.println("Response content: " + result);  
            }  
            System.out.println("------------------------------------");  
            
            
//            InputStream in=response.getEntity().getContent();
//            json=IOUtils.toString(in);
//            in.close();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {            
            if(response!=null){
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            }            
        }                
        return result;
    }
}
