package com.bootstrap.jimas.controller.web;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import com.bootstrap.jimas.common.Constant;
import com.bootstrap.jimas.common.ResultVo;

@Controller
public class LangController {
    private static final Logger logger = Logger.getLogger(LangController.class);
    @Autowired
    LocaleResolver localeResolver;
    
    @RequestMapping("/setlang")
    @ResponseBody
    public ResultVo setlang(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
        ResultVo resultVo = new ResultVo();
        try {
            String language = request.getParameter(Constant.COOKIE_LANG_KEY);
            parseLanguage(request,response,language);
            resultVo.setMessage("切换成功");
        } catch (Exception e) {
            logger.error("切换语言失败", e);
            return new ResultVo(500, "切换语言失败", null);
        }
        return resultVo;
    }

    /**
     * 解析并设置语种信息
     * @param request
     * @param response
     * @param language
     */
    private void parseLanguage(HttpServletRequest request, HttpServletResponse response,String language) {
        if(StringUtils.isEmpty(language)){
            language=Constant.DEFAULT_LOCAL_LANGUAGE;
        }
        if (language.equals("zh_CN")) {
            localeResolver.setLocale(request, response, Locale.CHINA);
        } 
        if(language.equals("zh")){
            localeResolver.setLocale(request, response, Locale.CHINESE);
        }
        if(language.equals("en_US")){
            localeResolver.setLocale(request, response, Locale.US);;
        }
        if(language.equals("en")){
            localeResolver.setLocale(request, response, Locale.ENGLISH);;
        }
    }
}
