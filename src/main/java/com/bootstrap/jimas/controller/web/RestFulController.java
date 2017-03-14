package com.bootstrap.jimas.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bootstrap.jimas.api.MenuResApi;
import com.bootstrap.jimas.api.WebLogApi;
import com.bootstrap.jimas.common.ResultVo;
import com.bootstrap.jimas.config.ParamsConfig;
import com.bootstrap.jimas.db.mongodb.domain.LogDomain;
import com.bootstrap.jimas.db.mongodb.request.BaseKeyReq;
import com.bootstrap.jimas.db.mongodb.response.MenuRes;
import com.bootstrap.jimas.utils.MD5Util;

/**
 * @Description restful 接口
 * @author weqinjia.liu
 * @Date 2017年3月13日
 */
@RestController
@RequestMapping("/rest")
public class RestFulController {
    @Autowired
    private ParamsConfig paramsConfig;
    @Autowired
    private MenuResApi menuResApi;
    @Autowired
    private WebLogApi webLogApi;
    /**
     * 保存菜单对象
     * @param req
     * @return
     */
    @RequestMapping(value = "/menu/saveMenu", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResultVo saveMenu(@RequestParam(required=true,value="token") String token,@RequestBody MenuRes menuRes){
        String tokenBak = MD5Util.MD5Encode(paramsConfig.getRestKey()+menuRes.getSiteSource());
        if(!tokenBak.equals(token)){// token 不匹配
            ResultVo resultVo = new ResultVo();
            resultVo.setMessage("token 不匹配");
            resultVo.setStatus(401);
            return resultVo;
        }
        ResultVo saveMenuRes = menuResApi.saveMenuRes(menuRes);
        return saveMenuRes;
    }
    
    /**
     * 保存菜单对象
     * @param req
     * @return
     */
    @RequestMapping(value = "/menu/queryMenu", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResultVo queryMenu(@RequestParam(required=true,value="token") String token,@RequestBody BaseKeyReq<String> siteSource){
        
        String tokenBak = MD5Util.MD5Encode(paramsConfig.getRestKey()+siteSource.getSiteSource());
        if(!tokenBak.equals(token)){// token 不匹配
            ResultVo resultVo = new ResultVo();
            resultVo.setMessage("token 不匹配");
            resultVo.setStatus(401);
            return resultVo;
        }
        return menuResApi.findMenuBySiteSource(siteSource.getSiteSource());
    }
    
    /**
     * 保存操作日期
     * @param req
     * @return
     */
    @RequestMapping(value = "/webLog/insertLog", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResultVo insertLog(@RequestParam(required=true,value="token") String token,@RequestBody LogDomain logDomain){
        
        String tokenBak = MD5Util.MD5Encode(paramsConfig.getRestKey()+logDomain.getSiteSource());
        if(!tokenBak.equals(token)){// token 不匹配
            ResultVo resultVo = new ResultVo();
            resultVo.setMessage("token 不匹配");
            resultVo.setStatus(401);
            return resultVo;
        }
        return webLogApi.insertLog(logDomain);
    }
    
    
    
    
}
