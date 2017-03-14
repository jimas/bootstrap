package com.bootstrap.jimas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParamsConfig {
    
    @Value("${result.mvcInterceptor.add.path}")
    private String[] mvcInterceptorAddPath;
    @Value("${result.mvcInterceptor.exclude.path}")
    private String[] mvcInterceptorExcludePath;
    @Value("${log.delete.date}")
    private Integer logDeleteDate;
    @Value("${rest.key}")
    private String restKey;
    @Value("${site.source}")
    private String siteSource;
    
    public String[] getMvcInterceptorAddPath() {
        return mvcInterceptorAddPath;
    }
    public String[] getMvcInterceptorExcludePath() {
        return mvcInterceptorExcludePath;
    }
    public Integer getLogDeleteDate() {
        return logDeleteDate;
    }
    public String getRestKey() {
        return restKey;
    }
    public String getSiteSource() {
        return siteSource;
    }
    
    
    

}
