package com.bootstrap.jimas.db.mongodb.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.bootstrap.jimas.db.mongodb.dao.MenuDomainRepository;
import com.bootstrap.jimas.db.mongodb.domain.MenuDomain;
@Service
public class MenuDomainService {
    /**
     * 默认排序字段
     */
    private static String orderBy="sortStr";
    @Autowired
    private MenuDomainRepository menuDomainRepository;
    
    public List<MenuDomain> findAllMenu(){
        Order orders=new Order(orderBy);
        Sort sort=new Sort(orders);
        List<MenuDomain> menuDomainList = menuDomainRepository.findAll(sort);
        return menuDomainList;
    }

    
}
