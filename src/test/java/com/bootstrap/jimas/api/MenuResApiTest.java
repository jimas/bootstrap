package com.bootstrap.jimas.api;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bootstrap.jimas.db.mongodb.BaseTest;
import com.bootstrap.jimas.db.mongodb.domain.Menu;
import com.bootstrap.jimas.db.mongodb.domain.MenuDomain;
import com.jimas.common.ResultVo;

public class MenuResApiTest extends BaseTest {

    @Autowired
    private MenuResApi menuResApi;
    
    @Test
    public void testSaveMenuRes() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteMenuRsBySiteSource() {
        fail("Not yet implemented");
    }

    @Test
    public void testFindMenuBySiteSource() {
        String siteSource="dbconn";
        ResultVo<MenuDomain<Menu>> resultVo = menuResApi.findMenuBySiteSource(siteSource);
        System.out.println(resultVo);
    }

}
