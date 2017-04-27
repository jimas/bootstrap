package com.bootstrap.jimas.controller.web;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bootstrap.jimas.interceptor.MenuModel;
import com.jimas.weixin.skytrap.repository.api.UserApi;
/**
 * @Description
 * @author weqinjia.liu
 * @Date 2016年12月28日
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserApi userApi;
    
    @MenuModel
    @RequestMapping("/userList")
    public String userList(ModelMap map,HttpRequest request,HttpResponse response){
    

        return "pages/user/userList";
    }
}
