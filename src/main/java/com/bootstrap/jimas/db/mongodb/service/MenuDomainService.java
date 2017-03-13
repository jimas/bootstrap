package com.bootstrap.jimas.db.mongodb.service;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bootstrap.jimas.common.ResultVo;
import com.bootstrap.jimas.db.mongodb.dao.MenuDomainRepository;
import com.bootstrap.jimas.db.mongodb.domain.MenuDomain;
@Service
public class MenuDomainService {
    /**
     * 默认排序字段
     */
    private static String orderBy="sortStr";
    private static final Logger logger=Logger.getLogger(MenuDomainService.class);
            
    @Autowired
    private MenuDomainRepository menuDomainRepository;
    
    public List<MenuDomain> findAllMenu(){
        Order orders=new Order(orderBy);
        Sort sort=new Sort(orders);
        List<MenuDomain> menuDomainList = menuDomainRepository.findAll(sort);
        return menuDomainList;
    }

    /**
     * 保存单个菜单对象
     * @param menuDomain
     * @return
     */
    public ResultVo saveMenuDomain(MenuDomain menuDomain){
        ResultVo resultVo = new ResultVo();
        try {
            Assert.notNull(menuDomain);
            MenuDomain save = menuDomainRepository.save(menuDomain);
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
    public ResultVo saveMenuBatch(List<MenuDomain> menuDomains){
        ResultVo resultVo = new ResultVo();
        try {
            Assert.notNull(menuDomains);
            List<MenuDomain> save = menuDomainRepository.save(menuDomains);
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
    public ResultVo deleteMenuRsBySiteSource(String siteSource) {
        ResultVo resultVo = new ResultVo();
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
    public ResultVo findMenuBySiteSource(String siteSource) {
        ResultVo resultVo = new ResultVo();
        try {
            Assert.notNull(siteSource);
            MenuDomain findOne = menuDomainRepository.findOne(siteSource);
            resultVo.setResult(findOne);
        } catch (Exception e) {
            resultVo.setStatus(400);
            resultVo.setMessage(e.getMessage());
            logger.error("MenuDomainService.findMenuBySiteSource error ",e);
        }
        return resultVo;
    }
    
}
