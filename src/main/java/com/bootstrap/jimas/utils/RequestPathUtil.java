package com.bootstrap.jimas.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class RequestPathUtil {
    private static final Logger logger=Logger.getLogger(RequestPathUtil.class);
    /**
     * 获取项目的家目录
     * @param request
     * @return
     */
    public static String getHomePath(HttpServletRequest request) {
        logger.info("Scheme==>"+request.getScheme());
        logger.info("ServerName==>"+request.getServerName());
        logger.info("ContextPath==>"+request.getContextPath());
        logger.info("RequestURL==>"+request.getRequestURL());
        logger.info("RequestURI==>"+request.getRequestURI());
        logger.info("getServletPath==>"+request.getServletPath());
        
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
        logger.info("homePath==>"+path);
        return path;
    }
}
