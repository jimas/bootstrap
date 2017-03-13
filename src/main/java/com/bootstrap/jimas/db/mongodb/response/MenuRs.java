package com.bootstrap.jimas.db.mongodb.response;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.bootstrap.jimas.db.mongodb.domain.BaseDomain;
import com.bootstrap.jimas.db.mongodb.domain.Menu;

/**
 * @Description 菜单返回类 
 * @author weqinjia.liu
 * @Date 2017年3月13日
 */
@Document(collection="menuRs")
public class MenuRs extends BaseDomain {

    /**
     * 
     */
    private static final long serialVersionUID = 5332462470091350325L;
    private String menuName;
    private String menuCode;
    private String parentCode;//父级code
    private String ancestorCode;//顶级Code
    private String menuUrl;//
    private String sortStr;//排序
    private String icon;//菜单icon
    private Integer level;//菜单等级
    
    private List<MenuRs> subMenuList;

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

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
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

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<MenuRs> getSubMenuList() {
        return subMenuList;
    }

    public void setSubMenuList(List<MenuRs> subMenuList) {
        this.subMenuList = subMenuList;
    }
    
    
    
}
