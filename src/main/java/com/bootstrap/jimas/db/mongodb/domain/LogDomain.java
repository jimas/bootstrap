package com.bootstrap.jimas.db.mongodb.domain;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;

//日志对象
public class LogDomain extends BaseDomain {

    private static final long serialVersionUID = 3425438064871919026L;
    
    //id属性是给mongodb用的，用@Id注解修饰
    @Id
    private String id;
    
    private String url;
    private String httpMethod;
    private String remoteAddr;
    private String classMethod;
    private String args;
    private Map parameters;
    
    private Date operateDate;
    
    private String siteSource;
    
    public Date getOperateDate() {
        return operateDate;
    }
    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getHttpMethod() {
        return httpMethod;
    }
    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }
    public String getRemoteAddr() {
        return remoteAddr;
    }
    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }
    public String getClassMethod() {
        return classMethod;
    }
    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }
    
    
    public String getArgs() {
        return args;
    }
    public void setArgs(String args) {
        this.args = args;
    }
    public Map getParameters() {
        return parameters;
    }
    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }
    public String getSiteSource() {
        return siteSource;
    }
    public void setSiteSource(String siteSource) {
        this.siteSource = siteSource;
    }
    
    

}
