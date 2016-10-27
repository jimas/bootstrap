package com.bootstrap.jimas.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class CookieUtil {

	public String encode(String value) {
		String v = value;
		try {
			v = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return v;
	}

	public String decode(String value) {
		String v = value;
		try {
			v = URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return v;
	}
	
	/**
	 * 保存cookie
	 * 
	 * @param response
	 * @param max_age cookie生命期(秒)
	 * @param key cookie-key
	 * @param value cookie-value
	 */
	public static void saveCookie(HttpServletResponse response,Integer max_age,String key,String value){
	    Cookie cookie = new Cookie(key, value);
	    cookie.setMaxAge(max_age);
	    cookie.setPath("/");
	    //cookie.setDomain(Constant.COOKIE_DOMAIN);
	    response.addCookie(cookie);
	}
	
	/**
	 * 保存cookie
	 * 
	 * @param response
	 * @param max_age cookie生命期(秒)
	 * @param cookie_key_values 要保存的cookie键值对
	 */
	public static void saveCookieArray(HttpServletResponse response,Integer max_age,Map<String,String> cookie_key_values){
	    for (String key : cookie_key_values.keySet()) {
	        Cookie cookie = new Cookie(key, cookie_key_values.get(key));
	        cookie.setMaxAge(max_age);
	        cookie.setPath("/");
		    //cookie.setDomain(Constant.COOKIE_DOMAIN);
	        response.addCookie(cookie);
        }
	}
	
	/**
	 * 获取cookie
	 * 
	 * @param request
	 * @param response
	 * @param key 要查找的cookie-key
	 */
	public static String getCookie(HttpServletRequest request,HttpServletResponse response,String key){
	    if(StringUtils.isEmpty(key)){
	        return null;
	    }
	    Cookie[] cookies = request.getCookies();
	    //cookies不为空
	    if (cookies != null) {
	        for (int i = 0; i < cookies.length; i++) {
	            if (key.equals(cookies[i].getName())) {
	                return  cookies[i].getValue();
	            }
	        }
	    }
	    return null;
	}
	
	/**
	 * 清除cookie
	 * 
	 * @param request
	 * @param response
	 * @param key 要清除的cookie-key
	 */
	public static void clearCookie(HttpServletRequest request,HttpServletResponse response,String key){
	    if(StringUtils.isEmpty(key)){
	        return ;
	    }
	    Cookie[] cookies = request.getCookies();
        //cookies不为空
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (key.equals(cookies[i].getName())) {
                    cookies[i].setValue(null);
                    cookies[i].setMaxAge(0);
                    cookies[i].setPath("/");
            	    //cookies[i].setDomain(Constant.COOKIE_DOMAIN);
                    response.addCookie(cookies[i]);
                    break;
                }
            }
        }
	}
	
	/**
	 * 清除cookie
	 * 
	 * @param request
	 * @param response
	 * @param key_list 要清除的cookie-key列表
	 */
	public static void clearCookie(HttpServletRequest request,HttpServletResponse response,List<String> key_list){
	    if(key_list == null || key_list.size() < 1){
	        return ;
	    }
	    Cookie[] cookies = request.getCookies();
	    //cookies不为空
	    if (cookies != null) {
	        for (int i = 0; i < cookies.length; i++) {
	            if (key_list.contains(cookies[i].getName())) {
	                cookies[i].setValue(null);
	                cookies[i].setMaxAge(0);
	                cookies[i].setPath("/");
            	    //cookies[i].setDomain(Constant.COOKIE_DOMAIN);
	                response.addCookie(cookies[i]);
	            }
	        }
	    }
	}
}
