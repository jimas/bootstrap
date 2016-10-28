package com.bootstrap.jimas.controller.metronic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MetronicIndexController {
    @RequestMapping({"/Mindex"})
    public String index(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        
        return "metronic/Mindex";  
    }
}
