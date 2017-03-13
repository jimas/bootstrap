package com.bootstrap.jimas.db.mongodb.response;

import java.util.List;
/**
 * @Description 菜单返回类 
 * @author weqinjia.liu
 * @Date 2017年3月13日
 */
public class MenuRes extends BaseRes{

    private static final long serialVersionUID = 6880716860489845128L;

    private String siteSource;//来源系统
    
    private List<?> menuList;//菜单列表

    public String getSiteSource() {
        return siteSource;
    }

    public void setSiteSource(String siteSource) {
        this.siteSource = siteSource;
    }

    public List<?> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<?> menuList) {
        this.menuList = menuList;
    }
    
}
