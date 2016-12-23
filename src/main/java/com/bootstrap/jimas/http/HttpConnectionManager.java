package com.bootstrap.jimas.http;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.stereotype.Component;

/**
 * @Description 连接池管理类，支持https协议
 * @author weqinjia.liu
 * @Date 2016年12月23日
 */
@Component
public class HttpConnectionManager {
    PoolingHttpClientConnectionManager cm = null;

    @PostConstruct
    public void init(){
        LayeredConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Registry<ConnectionSocketFactory> socketFactoryRegistry  = RegistryBuilder.<ConnectionSocketFactory> create()
               .register("https", sslsf)
               .register("http", new PlainConnectionSocketFactory())
               .build();
        cm=  new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(200);
        
    }
    
    public CloseableHttpClient  getHttpClient(){
//        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        CloseableHttpClient httpClient = HttpClients.createDefault();//如果不采用连接池就是这种方式获取连接
        return httpClient;
    }
}
