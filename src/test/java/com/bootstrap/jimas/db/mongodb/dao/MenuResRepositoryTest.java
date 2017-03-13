package com.bootstrap.jimas.db.mongodb.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.bootstrap.jimas.api.MenuResApi;
import com.bootstrap.jimas.common.ResultVo;
import com.bootstrap.jimas.db.mongodb.BaseTest;
import com.bootstrap.jimas.db.mongodb.domain.Menu;
import com.bootstrap.jimas.db.mongodb.response.MenuRes;
import com.bootstrap.jimas.db.mongodb.response.MenuRs;

public class MenuResRepositoryTest extends BaseTest {
    @Autowired
    private MenuRsRepository menuRsRepository;
    @Autowired
    private MenuResApi menuRsService;
    @Value("${site.source}")
    private String siteSource;
    @Test
    public void testSaveMenuRes() {
        List<MenuRs> menuRsList = menuRsRepository.findAll();
        MenuRes menuRes=new MenuRes();
        
        List<Menu> menuList=new ArrayList<Menu>();
        
        for (MenuRs menuRs : menuRsList) {
            Menu menu = convertMenu(menuRs);
            
            menuList.add(menu);
        }
        
        
        
        menuRes.setSiteSource(siteSource);
        menuRes.setMenuList(menuList);
        ResultVo saveMenuRs = menuRsService.saveMenuRes(menuRes);
        
        System.out.println(saveMenuRs);
    }
    private Menu convertMenu(MenuRs menuRs) {
        Menu menu = new Menu();// 一级菜单
        menu.setAncestorCode(menuRs.getAncestorCode());
        menu.setIcon(menuRs.getIcon());
        menu.setLevel(menuRs.getLevel());
        menu.setMenuCode(menuRs.getMenuCode());
        menu.setMenuName(menuRs.getMenuName());
        menu.setMenuUrl(menuRs.getMenuUrl());
        menu.setParentCode(menuRs.getParentCode());
        menu.setSortStr(menuRs.getSortStr());

        List<MenuRs> subMenuList = menuRs.getSubMenuList();//二级菜单
        if(!CollectionUtils.isEmpty(subMenuList)){
            List<Menu> MenuList=new ArrayList<Menu>();
            for (MenuRs menuRs2 : subMenuList) {
                Menu convertMenu = convertMenu(menuRs2);
                MenuList.add(convertMenu);
            }
            menu.setSubMenuList(MenuList);
        }
        return menu;
    }

}
