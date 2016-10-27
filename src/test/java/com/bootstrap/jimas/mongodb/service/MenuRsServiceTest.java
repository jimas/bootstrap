package com.bootstrap.jimas.mongodb.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bootstrap.jimas.db.mongodb.BaseTest;
import com.bootstrap.jimas.db.mongodb.response.MenuRs;
import com.bootstrap.jimas.db.mongodb.service.MenuRsService;

public class MenuRsServiceTest extends BaseTest {

    @Autowired
    private MenuRsService service;
    @Test
    public void testFindAllMenu() {
        List<MenuRs> menuList = service.findAllMenu();
        System.out.println(menuList);
    }

}
