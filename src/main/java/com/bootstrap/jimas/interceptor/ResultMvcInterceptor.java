package com.bootstrap.jimas.interceptor;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bootstrap.jimas.common.Constant;
import com.bootstrap.jimas.db.mongodb.domain.UserInfo;
import com.bootstrap.jimas.db.mongodb.response.MenuRs;
import com.bootstrap.jimas.db.mongodb.service.MenuRsService;
import com.bootstrap.jimas.db.mongodb.service.UserInfoService;
import com.bootstrap.jimas.utils.CookieUtil;

@Component
public class ResultMvcInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger(ResultMvcInterceptor.class);
    @Autowired
    private MenuRsService menuRsService;
    @Autowired
    private UserInfoService userInfoService;
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj,
            Exception paramException) throws Exception {

    }

    /**
     * 请求处理完成
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        System.out.println(response.getCharacterEncoding());
        if(handler instanceof HandlerMethod){
            HandlerMethod method =(HandlerMethod) handler;
            MenuModel menuModel = method.getMethodAnnotation(MenuModel.class);
            if(StringUtils.isEmpty(modelAndView)){
                modelAndView=new ModelAndView();
            }
            if(menuModel!=null&&menuModel.value()){
                List<MenuRs> menus = menuRsService.findAllMenu();
                modelAndView.addObject("listMenu", menus);
            }
            modelAndView.addObject(Constant.COOKIE_LANG_KEY,LocaleContextHolder.getLocale().toString());
            modelAndView.addObject(Constant.CURRENT_LOGIN_USER,CookieUtil.getCookie(request, response, Constant.CURRENT_LOGIN_USER));
        }
    }

    /**
     * 请求正式处理前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj)
            throws Exception {
        //获得请求地址
        StringBuffer requestUrl = request.getRequestURL();
        String path = parsePath(request);
        logger.info("request url ==>"+requestUrl);
        String current_user = CookieUtil.getCookie(request, response, Constant.CURRENT_LOGIN_USER);
        if(StringUtils.isEmpty(current_user)){
            response.sendRedirect(path+"/userLogin");
        }else{
            UserInfo user = userInfoService.findByUsername(current_user);
            if(StringUtils.isEmpty(user)){
                response.sendRedirect(path+"userLogin");
            }
        }
        return true;
    }

    private String parsePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String path = request.getScheme() + "://" + request.getServerName();
        if (80 != request.getServerPort()) {
            path += ":" + request.getServerPort();
        }
        if (!contextPath.startsWith("/")) {
            path += "/" + contextPath;
        } else {
            path += contextPath;
        }
        return path;
    }

}
