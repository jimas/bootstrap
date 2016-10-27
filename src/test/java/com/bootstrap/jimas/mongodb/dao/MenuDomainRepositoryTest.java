package com.bootstrap.jimas.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bootstrap.jimas.db.mongodb.BaseTest;
import com.bootstrap.jimas.db.mongodb.dao.MenuDomainRepository;
import com.bootstrap.jimas.db.mongodb.domain.MenuDomain;

public class MenuDomainRepositoryTest extends BaseTest {
    @Autowired
    private MenuDomainRepository menuDomainRepository;
    @Test
    public void testFindByMenuCode() {
        String menuCode="sub-menu-1";
        MenuDomain menuDomain = menuDomainRepository.findByMenuCode(menuCode);
        System.out.println(menuDomain);
    }
//    @Test
    public void testSaveList() {
        ArrayList<MenuDomain> menuDomainList=new ArrayList<MenuDomain>();
       for(int i=0;i<3;i++){
           MenuDomain menuDomain = new MenuDomain();
           menuDomain.setAncestorCode("Layouts");
           menuDomain.setIcon("icon-user");
           menuDomain.setMenuCode("sub-menu-"+(i+1));
           menuDomain.setMenuName("子菜单"+(i+1));
           menuDomain.setMenuUrl(null);
           menuDomain.setParentCode("Layouts");
           menuDomain.setSortStr("B"+(i+1));
           menuDomain.setLevel(2);
           menuDomainList.add(menuDomain);
       }
       List list = menuDomainRepository.save(menuDomainList);
       System.out.println(list);
    }
//    @Test
    public void testSave() {
//        MenuDomain menuDomain = new MenuDomain();
//        menuDomain.setAncestorCode(null);
//        menuDomain.setIcon("icon-cogs");
//        menuDomain.setMenuCode("Layouts");
//        menuDomain.setMenuName("布局");
//        menuDomain.setMenuUrl(null);
//        menuDomain.setParentCode(null);
//        menuDomain.setSortStr("B");
//        menuDomain.setLevel(1);
//        MenuDomain save = menuDomainRepository.save(menuDomain);
//        System.out.println(save);
        MenuDomain menuDomain1 = new MenuDomain();
        menuDomain1.setAncestorCode(null);
        menuDomain1.setIcon("icon-home");
        menuDomain1.setMenuCode("Dashboard");
        menuDomain1.setMenuName("仪表盘");
        menuDomain1.setMenuUrl("jimas.html");
        menuDomain1.setParentCode(null);
        menuDomain1.setSortStr("A");
        menuDomain1.setLevel(1);
        MenuDomain save1 = menuDomainRepository.save(menuDomain1);
        System.out.println(save1);
    }
//    @Test
    public void testFindAll() {
        List<MenuDomain> findAll = menuDomainRepository.findAll();
        for (MenuDomain menuDomain : findAll) {
            menuDomain.setLevel(1);
        }
    }

}
