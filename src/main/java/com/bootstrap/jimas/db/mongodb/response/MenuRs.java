package com.bootstrap.jimas.db.mongodb.response;

import java.util.List;

import com.bootstrap.jimas.db.mongodb.domain.MenuDomain;

public class MenuRs extends MenuDomain{
    
    private static final long serialVersionUID = 5548924027671252512L;

    
    private List<MenuRs> subMenuList;//子菜单列表


    public List<MenuRs> getSubMenuList() {
        return subMenuList;
    }


    public void setSubMenuList(List<MenuRs> subMenuList) {
        this.subMenuList = subMenuList;
    }

    
    

}
