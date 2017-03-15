package com.bootstrap.jimas.api;

import com.bootstrap.jimas.db.mongodb.domain.Menu;
import com.bootstrap.jimas.db.mongodb.domain.MenuDomain;
import com.jimas.common.ResultVo;

/**
 * @Description 菜单接口
 * @author weqinjia.liu
 * @Date 2017年3月13日
 */
public interface MenuResApi {

    
    /**
     * 保存菜单
     * @param menuRs
     * @return
     */
    public ResultVo<MenuDomain<Menu>> saveMenuRes(MenuDomain<Menu> menuRes);
    /**
     * 根据来源系统删除菜单
     * @param siteSource
     * @return 
     */
    public ResultVo<String> deleteMenuRsBySiteSource(String siteSource);
    /**
     * 根据主键查询菜单对象
     * @param siteSource
     * @return
     */
    public ResultVo<MenuDomain<Menu>> findMenuBySiteSource(String siteSource);
}
