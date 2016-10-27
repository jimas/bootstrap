package com.bootstrap.jimas.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/userLogin")
    public String userLogin(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        
        return "pages/user_login";  
    }
   
}
