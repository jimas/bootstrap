package com.bootstrap.jimas.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootstrap.jimas.common.Constant;
import com.bootstrap.jimas.common.ResultVo;
import com.bootstrap.jimas.db.mongodb.domain.UserInfo;
import com.bootstrap.jimas.db.mongodb.service.UserInfoService;
import com.bootstrap.jimas.utils.CookieUtil;

@Controller
public class LoginController {
    @Autowired
    private UserInfoService userInfoService;
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
        return "redirect:/index";
    }
    /**
     * 跳转到 主页
     * @param userInfo
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/gotoJimas",method=RequestMethod.POST)
    public ResultVo gotoJimas(UserInfo userInfo,HttpServletRequest request, HttpServletResponse response){
        ResultVo resultVo = new ResultVo();
        String current_login_user = CookieUtil.getCookie(request, response, Constant.CURRENT_LOGIN_USER);
        if(StringUtils.isEmpty(current_login_user)){
            String username = userInfo.getUsername();
            String password = userInfo.getPassword();
            userInfo = userInfoService.findByUsernameAndPassword(username, password);
            if(StringUtils.isEmpty(userInfo)){
                resultVo.setStatus(400);
                resultVo.setMessage("用户名密码错误");
                return resultVo;
            }
            CookieUtil.saveCookie(response, Constant.max_age, Constant.CURRENT_LOGIN_USER, username);
        }
        return resultVo;
    }
   
}
