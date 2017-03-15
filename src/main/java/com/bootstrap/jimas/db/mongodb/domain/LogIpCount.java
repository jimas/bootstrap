package com.bootstrap.jimas.db.mongodb.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * @Description 日志统计对象
 * @author weqinjia.liu
 * @Date 2017年3月15日
 * 
 * 1、我需要看出有哪些ip访问我，每个ip访问多少次。   siteSource remoteAddr access_count  operateDate 
 * 2、访问的 url 次数
 * 3、分不同的系统统计
 * 4、分时间段统计
 * 5、post、get 请求次数
 */
@Document(collection = "logIpCount")
public class LogIpCount extends BaseDomain{
    
    private static final long serialVersionUID = 1388703912853889468L;
    
    //id属性是给mongodb用的，用@Id注解修饰
    @Id
    private String id;//siteSource + operateDate + remoteAddr
    //来源系统
    private String siteSource;
    //remoteAddr
    private String remoteAddr;
    //操作时间
    private Date operateDate;
    //访问次数
    private Long access_count;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSiteSource() {
        return siteSource;
    }
    public void setSiteSource(String siteSource) {
        this.siteSource = siteSource;
    }
    public String getRemoteAddr() {
        return remoteAddr;
    }
    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }
    public Date getOperateDate() {
        return operateDate;
    }
    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }
    public Long getAccess_count() {
        return access_count;
    }
    public void setAccess_count(Long access_count) {
        this.access_count = access_count;
    } 
    
    

}
