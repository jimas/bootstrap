package com.bootstrap.jimas.db.mongodb.domain;

import org.springframework.data.annotation.Id;
/**
 * @Description 菜单实体类
 * @author weqinjia.liu
 * @Date 2016年10月9日
 */
public class MenuDomain extends BaseDomain {

    private static final long serialVersionUID = 5380460817656498120L;
    
    //id属性是给mongodb用的，用@Id注解修饰
    @Id
    private String id;
    private String menuName;
    private String menuCode;
    private String parentCode;//父级code
    private String ancestorCode;//顶级Code
    private String menuUrl;//
    private String sortStr;//排序
    private String icon;//菜单icon
    private Integer level;//菜单等级
    
    
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    public String getSortStr() {
        return sortStr;
    }
    public void setSortStr(String sortStr) {
        this.sortStr = sortStr;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getMenuName() {
        return menuName;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public String getMenuCode() {
        return menuCode;
    }
    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
    public String getAncestorCode() {
        return ancestorCode;
    }
    public void setAncestorCode(String ancestorCode) {
        this.ancestorCode = ancestorCode;
    }
    public String getMenuUrl() {
        return menuUrl;
    }
    
    public String getParentCode() {
        return parentCode;
    }
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }
    
    
    

}
