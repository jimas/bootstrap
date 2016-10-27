package com.bootstrap.jimas.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import com.bootstrap.jimas.db.mongodb.domain.MenuDomain;
import com.bootstrap.jimas.db.mongodb.response.MenuRs;
import com.bootstrap.jimas.db.mongodb.service.LogService;
import com.bootstrap.jimas.db.mongodb.service.MenuDomainService;
import com.bootstrap.jimas.db.mongodb.service.MenuRsService;

/**
 * @Description 定时任务配置类
 * @author weqinjia.liu
 * @Date 2016年10月9日
 */
@Configuration
@EnableScheduling // 启用定时任务
public class SchedulingConfig {
    
    private final Logger logger = Logger.getLogger(getClass());
    @Autowired
    private MenuRsService menuRsService;
    @Autowired
    private MenuDomainService menuDomainService;
    @Autowired
    private LogService logService;
    /**
     * 同步菜单
     */
    @Scheduled(cron = "0 0/2 * * * ?") // 每2分钟执行一次
    public List<MenuRs> schedulerMenu() {
        logger.info("sync menu start");
        List<MenuDomain> menuDomainList = menuDomainService.findAllMenu();
        List<MenuRs> list = convertMenuRs(menuDomainList);
        menuRsService.saveBatch(list);
        logger.info("sync menu end");
        return list;
    }
    
    /**
     * 删除过期日志  只删除5天前的日志
     */
    @Scheduled(cron = "0 0/10 * * * ?") // 每10分钟执行一次
    public void schedulerLog() {
        logger.info("delete 5DaysAgo log start ");
        try {
            logService.delete5DaysAgo();
        } catch (Exception e) {
            logger.error("删除过期日志失败",e);
        }
        logger.info("delete 5DaysAgo log end ");
    }
    
    
    /*========================================== private method ========================================================*/
    /**
     * 转换成 MenuRs 对象
     * @param menuDomainList
     * @return
     */
    private List<MenuRs> convertMenuRs(List<MenuDomain> menuDomainList) {
        if(CollectionUtils.isEmpty(menuDomainList)){
            return Collections.emptyList();
        }
        List<MenuRs> list = new ArrayList<MenuRs>();
        for (MenuDomain menuDomain : menuDomainList) {
            if(menuDomain.getLevel()==1){//一级菜单
                MenuRs menuRs = new MenuRs();
                List<MenuRs> subMenuList = createMenuRs(menuDomainList, menuDomain, menuRs);
                menuRs.setSubMenuList(subMenuList);
                list.add(menuRs);
            }
        }
        return list;
    }

    /**
     * 创建MenuRs
     * @param menuDomainList
     * @param menuDomain
     * @param menuRs
     * @return
     */
    private List<MenuRs> createMenuRs(List<MenuDomain> menuDomainList, MenuDomain menuDomain, MenuRs menuRs) {
        menuRs.setAncestorCode(menuDomain.getAncestorCode());
        menuRs.setIcon(menuDomain.getIcon());
        menuRs.setId(menuDomain.getId());
        menuRs.setMenuCode(menuDomain.getMenuCode());
        menuRs.setMenuName(menuDomain.getMenuName());
        menuRs.setMenuUrl(menuDomain.getMenuUrl());
        menuRs.setLevel(menuDomain.getLevel());
        menuRs.setParentCode(menuDomain.getParentCode());
        menuRs.setSortStr(menuDomain.getSortStr());
        List<MenuRs> subMenuList=getSubMenuList(menuDomain.getMenuCode(),menuDomainList);//得到子菜单
        menuRs.setSubMenuList(subMenuList);
        return subMenuList;
    }

    /**
     * 得到子菜单
     * @param menuDomainList
     * @return
     */
    private List<MenuRs> getSubMenuList(String parentCode,List<MenuDomain> menuDomainList) {
        List<MenuRs> list = new ArrayList<MenuRs>();
        for (MenuDomain menuDomain : menuDomainList) {
            if(parentCode.equals(menuDomain.getParentCode())){
                MenuRs menuRs = new MenuRs();
                List<MenuRs> subMenuList = createMenuRs(menuDomainList, menuDomain, menuRs);
                menuRs.setSubMenuList(subMenuList);
                list.add(menuRs);
            }
        }
        return list;
    }
}