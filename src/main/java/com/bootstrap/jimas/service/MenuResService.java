package com.bootstrap.jimas.service;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.bootstrap.jimas.api.MenuResApi;
import com.bootstrap.jimas.common.Constant;
import com.bootstrap.jimas.db.mongodb.domain.Menu;
import com.bootstrap.jimas.db.mongodb.domain.MenuDomain;
import com.bootstrap.jimas.db.mongodb.service.MenuDomainService;
import com.jimas.common.ResultVo;
/**
 * @Description 在支持Spring Cache的环境下，对于使用 @Cacheable标注的方法，
 * Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，如果存在就不再执行该方法，
 * 而是直接从缓存中获取结果进行返回，否则才会执行并将 返回结果  存入指定的缓存中。
 *  @CachePut也可以声明一个方法支持缓存功能。 与
 *  @Cacheable不同的是使用 @CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，
 * 而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中
 * @author weqinjia.liu
 * @Date 2016年10月14日
 */
@Service("MenuResService")
public class MenuResService implements MenuResApi{
    
    private static final Logger logger=Logger.getLogger(MenuResService.class);
    @Autowired
    private MenuDomainService menuDomainService;
    
    /**
     * 查询 使用 缓存    
     * @return
     */
    @Cacheable(value=Constant.MENU_CACHE_NAME,key="'menu_'+#siteSource")
    @Override
    public ResultVo<MenuDomain<Menu>> findMenuBySiteSource(String siteSource) {
        logger.info(siteSource+"查询菜单start");
        ResultVo<MenuDomain<Menu>> resultVo= menuDomainService.findMenuBySiteSource(siteSource);
        logger.info(siteSource+"查询菜单end");
        return resultVo;
    }
    @Override
    @CachePut(value = Constant.MENU_CACHE_NAME,key="'menu_'+#menuRes.siteSource")
    public ResultVo<MenuDomain<Menu>> saveMenuRes(MenuDomain<Menu> menuRes) {
        ResultVo<MenuDomain<Menu>> resultVO = menuDomainService.saveMenuDomain(menuRes);
        return resultVO;
    }
    /**
     * 删除菜单并删除缓存
     */
    @Override
    @CacheEvict(value = Constant.MENU_CACHE_NAME,key="'menu_'+#siteSource")
    public ResultVo<String> deleteMenuRsBySiteSource(String siteSource) {
        return menuDomainService.deleteMenuRsBySiteSource(siteSource);
    }

    
}
