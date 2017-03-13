package com.bootstrap.jimas.api;

import com.bootstrap.jimas.common.ResultVo;
import com.bootstrap.jimas.db.mongodb.response.MenuRes;

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
    public ResultVo saveMenuRes(MenuRes menuRes);
    /**
     * 根据来源系统删除菜单
     * @param siteSource
     * @return 
     */
    public ResultVo deleteMenuRsBySiteSource(String siteSource);
    /**
     * 根据主键查询菜单对象
     * @param siteSource
     * @return
     */
    public ResultVo findMenuBySiteSource(String siteSource);
}
