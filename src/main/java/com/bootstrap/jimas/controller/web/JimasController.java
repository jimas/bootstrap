package com.bootstrap.jimas.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bootstrap.jimas.common.Constant;
import com.bootstrap.jimas.db.mongodb.domain.UserInfo;
import com.bootstrap.jimas.db.mongodb.service.MenuRsService;
import com.bootstrap.jimas.db.mongodb.service.UserInfoService;
import com.bootstrap.jimas.interceptor.MenuModel;
import com.bootstrap.jimas.task.AsyncTask;
import com.bootstrap.jimas.task.SyncTask;
import com.bootstrap.jimas.utils.CookieUtil;

@Controller
public class JimasController {
    private static final Logger logger=Logger.getLogger(JimasController.class);
    @Autowired
    private AsyncTask asyncTask;
    @Autowired
    private SyncTask syncTask;
    @Autowired
    private MenuRsService menuRsService;
    @Autowired
    private UserInfoService userInfoService;
    
    @RequestMapping(value="/jimas")
    @MenuModel
    public String index(UserInfo userInfo,HttpServletRequest request, HttpServletResponse response) {
        String current_login_user = CookieUtil.getCookie(request, response, Constant.CURRENT_LOGIN_USER);
        if(StringUtils.isEmpty(current_login_user)){
            String username = userInfo.getUsername();
            String password = userInfo.getPassword();
            userInfo = userInfoService.findByUsernameAndPassword(username, password);
            if(StringUtils.isEmpty(userInfo)){
                return "redirect:/userLogin";  
            }
            CookieUtil.saveCookie(response, Constant.max_age, Constant.CURRENT_LOGIN_USER, username);
        }
        return "pages/jimas";  
    }
    @RequestMapping("/sub_one")
    @MenuModel
    public String subOne(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        
        return "pages/sub_one";  
    }
    @RequestMapping("/sub_one_one")
    @MenuModel
    public String subOneOne(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        logger.info("asyncTask start");
        try {
            asyncTask.doTaskOne();
            asyncTask.doTaskTwo();
            asyncTask.doTaskThree();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pages/sub_one_one";  
    }
    @RequestMapping("/sub_one_two")
    @MenuModel
    public String subOneTwo(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        logger.info("syncTask start");
        try {
            syncTask.doTaskOne();
            syncTask.doTaskTwo();
            syncTask.doTaskThree();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pages/sub_one_two";  
    }
    @RequestMapping("/sub_one_three")
    @MenuModel
    public String subOneThree(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        String str=null;
        str.length();//TODO 模拟异常
        return "pages/sub_one_three";  
    }
    @RequestMapping("/sub_two")
    @MenuModel
    public String subTwo(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        menuRsService.deleteAllMenuRs();
        return "pages/sub_two";  
    }
    @RequestMapping("/sub_three")
    @MenuModel
    public String subThree(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        menuRsService.deleteAllMenuRsAndCache();
        return "pages/sub_three";  
    }
}
