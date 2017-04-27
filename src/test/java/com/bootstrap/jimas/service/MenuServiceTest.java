package com.bootstrap.jimas.service;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.bootstrap.jimas.api.MenuResApi;
import com.bootstrap.jimas.db.mongodb.BaseTest;
import com.bootstrap.jimas.db.mongodb.domain.Menu;
import com.bootstrap.jimas.db.mongodb.domain.MenuDomain;
import com.jimas.common.ResultVo;
import com.jimas.weixin.skytrap.repository.api.MenuButtonApi;
import com.jimas.weixin.skytrap.repository.entity.MenuButton;

public class MenuServiceTest extends BaseTest {

    @Autowired
    private MenuButtonApi service;
    
    @Autowired
    private MenuResApi menuResApi;
    
    @Test
    public void testSaveMenuButton() {
        String siteSource="bootstrap";
        ResultVo<MenuDomain<Menu>> rs = menuResApi.findMenuBySiteSource(siteSource);
        MenuDomain<Menu> result = rs.getResult();
        List<Menu> menuList = result.getMenuList();
        if(!CollectionUtils.isEmpty(menuList)){
            saveMenu(siteSource, menuList);
        }
        
    }

    private void saveMenu(String siteSource, List<Menu> menuList) {
        for (Menu menuA : menuList) {//一级菜单
            MenuButton menuButton=new MenuButton();
            menuButton.setAncestorCode(menuA.getAncestorCode());
            menuButton.setCode(menuA.getMenuCode());
            menuButton.setLevel(menuA.getLevel());
            menuButton.setName(menuA.getMenuName());
            menuButton.setOperateUrl(StringUtils.isEmpty(menuA.getMenuUrl())?"#":menuA.getMenuUrl());
            menuButton.setParentCode(menuA.getParentCode());
            menuButton.setSiteSource(siteSource);
            menuButton.setSortStr(menuA.getSortStr());
            menuButton.setIcon(menuA.getIcon());
            menuButton.setStatus("A");
            menuButton.setType("menu");
            boolean save = service.saveMenuButton(menuButton);
            System.out.println("["+save+"]"+menuButton);
            
            List<Menu> subMenuList = menuA.getSubMenuList();
            if(!CollectionUtils.isEmpty(subMenuList)){
                saveMenu(siteSource,subMenuList);
            }
        }
    }

    @Test
    public void testDeleteByKey() {
        fail("Not yet implemented");
    }

    @Test
    public void testFindList() {
        fail("Not yet implemented");
    }

    @Test
    public void testFindByKey() {
        fail("Not yet implemented");
    }

}
