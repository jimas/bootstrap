package com.bootstrap.jimas.db.mongodb.service;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bootstrap.jimas.common.Constant;
import com.bootstrap.jimas.db.mongodb.dao.MenuRsRepository;
import com.bootstrap.jimas.db.mongodb.response.MenuRs;
import com.bootstrap.jimas.task.SchedulingConfig;
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
@Service
public class MenuRsService {
    
    private static final Logger logger=Logger.getLogger(MenuRsService.class);
    /**
     * 默认排序字段
     */
    private static String orderBy="sortStr";
    /**
     * 所有菜单(此key 代表所有菜单)
     */
    private static final String ALL_MENU_KEY="'menuListRs'";
    @Autowired
    private MenuRsRepository menuRsRepository;
    @Autowired
    private SchedulingConfig menuConfig;
    
    /**
     * 查询 使用 缓存    
     * @return
     */
    @Cacheable(value=Constant.MENU_CACHE_NAME,key=ALL_MENU_KEY)
    public List<MenuRs> findAllMenu(){
        logger.info("查询菜单 start 并没有使用 缓存 ");
        Order orders=new Order(orderBy);
        Sort sort=new Sort(orders);
        List<MenuRs> list = menuRsRepository.findAll(sort);
        logger.info("查询菜单 end 设置了缓存 ");
        if(CollectionUtils.isEmpty(list)){
            return menuConfig.schedulerMenu();
        }
        return list;
    }
    /**
     *  批量更新保存  并更新缓存
     * @param list
     */
    @CachePut(value = Constant.MENU_CACHE_NAME,key=ALL_MENU_KEY)
    public List<MenuRs> saveBatch(List<MenuRs> list) {
        logger.info("批量更新保存  并更新缓存");
        
        return menuRsRepository.save(list);
    }
    /**
     * 删除所有数据. 并删除缓存
     */
    @CacheEvict(value = Constant.MENU_CACHE_NAME,key=ALL_MENU_KEY)
    public void deleteAllMenuRsAndCache(){
        logger.info("删除所有数据. 并删除缓存");
        menuRsRepository.deleteAll();
    }
    /**
     * 删除所有数据.
     */
    public void deleteAllMenuRs(){
        logger.info("删除所有数据. ");
        menuRsRepository.deleteAll();
    }
}
