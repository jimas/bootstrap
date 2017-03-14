package com.bootstrap.jimas.db.mongodb.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * @Description 菜单实体类
 * @author weqinjia.liu
 * @Date 2016年10月9日
 */
@Document(collection = "jimasMenu")
public class MenuDomain<T> extends BaseDomain {

    private static final long serialVersionUID = 5380460817656498120L;
    
    @Id
    private String siteSource;//来源系统
    
    private List<T> menuList;//菜单列表

    public String getSiteSource() {
        return siteSource;
    }

    public void setSiteSource(String siteSource) {
        this.siteSource = siteSource;
    }

    public List<T> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<T> menuList) {
        this.menuList = menuList;
    }
    
    

}
