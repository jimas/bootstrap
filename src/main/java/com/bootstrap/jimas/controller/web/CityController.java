package com.bootstrap.jimas.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bootstrap.jimas.common.Constant;
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
    private static final Logger logger=Logger.getLogger(CityController.class);
    @Autowired
    private CityService cityService;
    @RequestMapping("cityPage")
    @MenuModel
    public String cityPage(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        SpringDataPageable pageReq=new SpringDataPageable();
        String parameter = request.getParameter(Constant.CURRENTPAGE);
        if(!StringUtils.isEmpty(parameter)){
            pageReq.setPagenamber(Integer.parseInt(parameter));
        }
        Page<CityDomain> page= cityService.findByPage(pageReq);
        logger.info(page);
        map.addAttribute("page",page);
        return "pages/city/city";  
        
    }
}