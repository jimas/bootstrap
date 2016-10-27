package com.bootstrap.jimas.controller.metronic;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

@Controller
public class HelloController {
    @Autowired
    LocaleResolver localeResolver;

    
    @RequestMapping("/hello")
    public String hello(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("host", "http://blog.didispace.com");
        map.addAttribute("jimas", "hello world");
        map.addAttribute("templateName", "thymeleaf模板");
        map.addAttribute("today", new Date());
        // return模板文件的名称，对应src/main/resources/templates/hello.html
        return "metronic/hello";  
    }
    @RequestMapping("/demoEn")
    public String demoEn(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("host", "http://blog.didispace.com");
        map.addAttribute("jimas", "hello world");
        map.addAttribute("templateName", "thymeleaf模板");
        map.addAttribute("today", new Date());
//        localeResolver.resolveLocale(request);
        localeResolver.setLocale(request,response,Locale.ENGLISH);
        return "demotest";  
    }
    @RequestMapping("/demoZh")
    public String demoZh(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("host", "http://blog.didispace.com");
        map.addAttribute("jimas", "hello world");
        map.addAttribute("templateName", "thymeleaf模板");
        map.addAttribute("today", new Date());
        localeResolver.setLocale(request,response,Locale.CHINA);
        return "demotest";  
    }
}