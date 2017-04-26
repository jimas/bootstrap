package com.bootstrap.jimas.api;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bootstrap.jimas.db.mongodb.BaseTest;
import com.bootstrap.jimas.db.mongodb.domain.Menu;
import com.bootstrap.jimas.db.mongodb.domain.MenuDomain;
import com.jimas.common.ResultVo;
import com.jimas.weixin.skytrap.repository.api.RoleApi;
import com.jimas.weixin.skytrap.repository.entity.Role;

public class MenuResApiTest extends BaseTest {

    @Autowired
    private MenuResApi menuResApi;
    @Autowired
    private RoleApi roleApi;
    
    @Test
    public void testFindById() {
        String roleId="";
        Role role = roleApi.findById(roleId);
        System.out.println(role);
    }
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
