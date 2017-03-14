package com.bootstrap.jimas.controller.web;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootstrap.jimas.common.ResultVo;
import com.bootstrap.jimas.db.mongodb.domain.Menu;
import com.bootstrap.jimas.db.mongodb.domain.MenuDomain;
import com.bootstrap.jimas.db.mongodb.service.MenuDomainService;
import com.bootstrap.jimas.interceptor.MenuModel;
/**
 * @Description 菜单控制类
 * @author weqinjia.liu
 * @Date 2016年12月28日
 */
@Controller
public class MenuController {

    @Autowired
    private MenuDomainService menuDomainService;
    @RequestMapping("showMenuList")
    @MenuModel
    public String showMenuList(ModelMap map,HttpRequest request,HttpResponse response){
    
        List<MenuDomain<Menu>> menuDomainList = menuDomainService.findAllMenu();
        map.put("menuList", menuDomainList);

        return "pages/menu/menu_list";
    }
    @RequestMapping("saveMenu")
    @ResponseBody
    public ResultVo<MenuDomain<Menu>> saveMenu(MenuDomain<Menu> menuDomain,HttpRequest request,HttpResponse response){
        return menuDomainService.saveMenuDomain(menuDomain);
    }
}
