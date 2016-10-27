package com.bootstrap.jimas.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice  
public class ExeptionHandler {  
  
    @Value("${server.error.path}")
    private String errorPath;
    private static final Logger logger=Logger.getLogger(ExeptionHandler.class);
    @ExceptionHandler(value = Exception.class)  
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {  
        ModelAndView mv = new ModelAndView(); 
        mv.setViewName(errorPath);// 不定义也行，但会继续执行原始的视图 会显示模板解析错误（不应该是这种错误啊）
        mv.addObject("e", e);  
        mv.addObject("uri", req.getRequestURI());  
        logger.error(" system error ", e);
        return mv;  
    }  
}  