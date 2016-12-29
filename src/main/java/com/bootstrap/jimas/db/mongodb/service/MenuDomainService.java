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

    public ResultVo saveMenuDomain(MenuDomain menuDomain){
        ResultVo resultVo = new ResultVo();
        Assert.notNull(menuDomain);
        try {
            menuDomainRepository.save(menuDomain);
        } catch (Exception e) {
            resultVo.setStatus(400);
            resultVo.setMessage(e.getMessage());
            logger.error("MenuDomainService.saveMenuDomain error ",e);
        }
        return resultVo;
    }
    
}
