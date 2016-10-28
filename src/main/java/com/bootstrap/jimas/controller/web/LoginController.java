package com.bootstrap.jimas.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bootstrap.jimas.common.Constant;
import com.bootstrap.jimas.db.mongodb.domain.UserInfo;
import com.bootstrap.jimas.utils.CookieUtil;

@Controller
public class LoginController {
    
    /**
     * 登录
     * @param map
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/userLogin")
    public String userLogin(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        map.addAttribute("userInfo", new UserInfo());
        return "pages/user_login";  
    }
    /**
     * 退出登录
     * @param map
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/LogOut",method=RequestMethod.GET)
    public String logOut(ModelMap map,HttpServletRequest request,HttpServletResponse response){
        //清除cookie
        CookieUtil.clearCookie(request, response, Constant.CURRENT_LOGIN_USER);
        return "pages/index";
    }
   
}
