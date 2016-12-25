package com.bootstrap.jimas.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bootstrap.jimas.common.SpringDataPageable;
import com.bootstrap.jimas.db.mongodb.domain.CityDomain;
import com.bootstrap.jimas.db.mongodb.service.CityService;
import com.bootstrap.jimas.interceptor.MenuModel;
/**
 * @Description 城市action
 * @author weqinjia.liu
 * @Date 2016年12月24日
 */
@Controller
public class CityController {
    @Autowired
    private CityService cityService;
    @RequestMapping("cityPage")
    @MenuModel
    public String cityPage(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        SpringDataPageable pageReq=new SpringDataPageable();
        Page<CityDomain> page= cityService.findByPage(pageReq);
        map.addAttribute("page",page);
        return "pages/city/city";  
    }
}
