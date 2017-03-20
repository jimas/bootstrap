package com.bootstrap.jimas.db.mongodb.service;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bootstrap.jimas.db.mongodb.dao.MenuDomainRepository;
import com.bootstrap.jimas.db.mongodb.domain.Menu;
import com.bootstrap.jimas.db.mongodb.domain.MenuDomain;
import com.jimas.common.ResultVo;
@Service
public class MenuDomainService {
    private static final Logger logger=Logger.getLogger(MenuDomainService.class);
            
    @Autowired
    private MenuDomainRepository menuDomainRepository;
    
    public List<MenuDomain<Menu>> findAllMenu(){
        List<MenuDomain<Menu>> menuDomainList = menuDomainRepository.findAll();
        return menuDomainList;
    }

    /**
     * 保存单个菜单对象
     * @param menuDomain
     * @return
     */
    public ResultVo<MenuDomain<Menu>> saveMenuDomain(MenuDomain<Menu> menuDomain){
        ResultVo<MenuDomain<Menu>> resultVo = new ResultVo<MenuDomain<Menu>>();
        try {
            Assert.notNull(menuDomain);
            MenuDomain<Menu> save = menuDomainRepository.save(menuDomain);
            resultVo.setResult(save);
        } catch (Exception e) {
            resultVo.setStatus(400);
            resultVo.setMessage(e.getMessage());
            logger.error("MenuDomainService.saveMenuDomain error ",e);
        }
        return resultVo;
    }
    /**
     * 保存多个菜单对象
     * @param menuDomains
     * @return
     */
    public ResultVo<List<MenuDomain<Menu>>> saveMenuBatch(List<MenuDomain<Menu>> menuDomains){
        ResultVo<List<MenuDomain<Menu>>> resultVo = new ResultVo<List<MenuDomain<Menu>>>();
        try {
            Assert.notNull(menuDomains);
            List<MenuDomain<Menu>> save = menuDomainRepository.save(menuDomains);
            resultVo.setResult(save);
        } catch (Exception e) {
            resultVo.setStatus(400);
            resultVo.setMessage(e.getMessage());
            logger.error("MenuDomainService.saveMenuBatch error ",e);
        }
        return resultVo;
    }
    /**
     * 删除菜单
     * @param siteSource
     */
    public ResultVo<String> deleteMenuRsBySiteSource(String siteSource) {
        ResultVo<String> resultVo = new ResultVo<String>();
        try {
            Assert.notNull(siteSource);
            menuDomainRepository.delete(siteSource);
        } catch (Exception e) {
            resultVo.setStatus(400);
            resultVo.setMessage(e.getMessage());
            logger.error("MenuDomainService.deleteMenuRsBySiteSource error ",e);
        }
        return resultVo;
    }
    /**
     * 查询菜单
     * @param siteSource
     * @return
     */
    public ResultVo<MenuDomain<Menu>> findMenuBySiteSource(String siteSource) {
        ResultVo<MenuDomain<Menu>> resultVo = new ResultVo<MenuDomain<Menu>>();
        try {
            Assert.notNull(siteSource);
            MenuDomain<Menu> findOne = menuDomainRepository.findOne(siteSource);
            findOne.getSiteSource();
            List<Menu> menuList = (List<Menu>) findOne.getMenuList();
            if(!CollectionUtils.isEmpty(menuList)){
                menuListSort(menuList);
            }
            
            resultVo.setResult(findOne);
        } catch (Exception e) {
            resultVo.setStatus(400);
            resultVo.setMessage(e.getMessage());
            logger.error("MenuDomainService.findMenuBySiteSource error ",e);
        }
        return resultVo;
    }

    private void menuListSort(List<Menu> menuList) {

        menuList.sort(new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                return o1.getSortStr().compareTo(o2.getSortStr());
            }
        });
        
        for (Menu menu : menuList) {
            List<Menu> subMenuList = menu.getSubMenuList();
            if(!CollectionUtils.isEmpty(subMenuList)){
                menuListSort(subMenuList);
            }
        }
    }
    
}
