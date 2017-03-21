package com.bootstrap.jimas.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bootstrap.jimas.api.MenuResApi;
import com.bootstrap.jimas.api.WebLogApi;
import com.bootstrap.jimas.api.request.LogIpRq;
import com.bootstrap.jimas.api.request.LogStatisticsRq;
import com.bootstrap.jimas.api.request.LogUrlRq;
import com.bootstrap.jimas.api.response.LogStatisticsRs;
import com.bootstrap.jimas.api.response.LogUrlStatisticsRs;
import com.bootstrap.jimas.config.ParamsConfig;
import com.bootstrap.jimas.db.mongodb.domain.LogDomain;
import com.bootstrap.jimas.db.mongodb.domain.LogIpCount;
import com.bootstrap.jimas.db.mongodb.domain.LogUrlCount;
import com.bootstrap.jimas.db.mongodb.domain.Menu;
import com.bootstrap.jimas.db.mongodb.domain.MenuDomain;
import com.bootstrap.jimas.db.mongodb.request.BaseKeyReq;
import com.jimas.common.ResultVo;
import com.jimas.common.util.MD5Util;

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
    public ResultVo<MenuDomain<Menu>> saveMenu(@RequestParam(required=true,value="token") String token,@RequestBody MenuDomain<Menu> menuRes){
        String tokenBak = MD5Util.MD5Encode(paramsConfig.getRestKey()+menuRes.getSiteSource());
        if(!tokenBak.equals(token)){// token 不匹配
            ResultVo<MenuDomain<Menu>> resultVo = new ResultVo<MenuDomain<Menu>>();
            resultVo.setMessage("token 不匹配");
            resultVo.setStatus(401);
            return resultVo;
        }
        ResultVo<MenuDomain<Menu>> saveMenuRes = menuResApi.saveMenuRes(menuRes);
        return saveMenuRes;
    }
    
    /**
     * 保存菜单对象
     * @param req
     * @return
     */
    @RequestMapping(value = "/menu/queryMenu", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResultVo<MenuDomain<Menu>>  queryMenu(@RequestParam(required=true,value="token") String token,@RequestBody BaseKeyReq<String> siteSource){
        
        String tokenBak = MD5Util.MD5Encode(paramsConfig.getRestKey()+siteSource.getSiteSource());
        if(!tokenBak.equals(token)){// token 不匹配
            ResultVo<MenuDomain<Menu>>  resultVo = new ResultVo<MenuDomain<Menu>>();
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
    public ResultVo<LogDomain> insertLog(@RequestParam(required=true,value="token") String token,@RequestBody LogDomain logDomain){
        
        String tokenBak = MD5Util.MD5Encode(paramsConfig.getRestKey()+logDomain.getSiteSource());
        if(!tokenBak.equals(token)){// token 不匹配
            ResultVo<LogDomain> resultVo = new ResultVo<LogDomain>();
            resultVo.setMessage("token 不匹配");
            resultVo.setStatus(401);
            return resultVo;
        }
        return webLogApi.insertLog(logDomain);
    }
    /**
     * 统计系统 ip 访问量
     * @param token
     * @param logIpRq
     * @return
     */
    @RequestMapping(value = "/webLog/ipCountAccessBysiteSource", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResultVo<List<LogIpCount>> ipCountAccessBysiteSource(@RequestParam(required=true,value="token") String token,@RequestBody LogIpRq logIpRq){
        String tokenBak = MD5Util.MD5Encode(paramsConfig.getRestKey()+logIpRq.getSiteSource());
        if(!tokenBak.equals(token)){// token 不匹配
            ResultVo<List<LogIpCount>> resultVo = new ResultVo<>();
            resultVo.setMessage("token 不匹配");
            resultVo.setStatus(401);
            return resultVo;
        }
        return  webLogApi.statisticsLogIpCountAccess(logIpRq);
    }
    /**
     * 统计系统访问量
     * @param token
     * @param logIpRq
     * @return
     */
    @RequestMapping(value = "/webLog/countAccessBysiteSource", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResultVo<List<LogStatisticsRs>> countAccessBysiteSource(@RequestParam(required=true,value="token") String token,@RequestBody LogStatisticsRq logStatisticsRq){
        String tokenBak = MD5Util.MD5Encode(paramsConfig.getRestKey()+logStatisticsRq.getSiteSource());
        if(!tokenBak.equals(token)){// token 不匹配
            ResultVo<List<LogStatisticsRs>> resultVo = new ResultVo<>();
            resultVo.setMessage("token 不匹配");
            resultVo.setStatus(401);
            return resultVo;
        }
        return  webLogApi.statisticsSiteSourceAccess(logStatisticsRq);
    }
    /**
     * 统计系统url ip 访问量
     * @param token
     * @param logUrlRq
     * @return
     */
    @RequestMapping(value = "/webLog/ipCountAccessByUrlsiteSource", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResultVo<List<LogUrlCount>> ipCountAccessByUrlsiteSource(@RequestParam(required=true,value="token") String token,@RequestBody LogUrlRq logUrlRq){
        String tokenBak = MD5Util.MD5Encode(paramsConfig.getRestKey()+logUrlRq.getSiteSource());
        if(!tokenBak.equals(token)){// token 不匹配
            ResultVo<List<LogUrlCount>> resultVo = new ResultVo<>();
            resultVo.setMessage("token 不匹配");
            resultVo.setStatus(401);
            return resultVo;
        }
        return  webLogApi.statisticsLogUrlIpCountAccess(logUrlRq);
    }
    /**
     * 统计系统url 访问量
     * @param token
     * @param logUrlRq
     * @return
     */
    @RequestMapping(value = "/webLog/countAccessByUrlsiteSource", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResultVo<List<LogUrlStatisticsRs>> countAccessByUrlsiteSource(@RequestParam(required=true,value="token") String token,@RequestBody LogStatisticsRq logStatisticsRq){
        String tokenBak = MD5Util.MD5Encode(paramsConfig.getRestKey()+logStatisticsRq.getSiteSource());
        if(!tokenBak.equals(token)){// token 不匹配
            ResultVo<List<LogUrlStatisticsRs>> resultVo = new ResultVo<>();
            resultVo.setMessage("token 不匹配");
            resultVo.setStatus(401);
            return resultVo;
        }
        
        return  webLogApi.statisticsSiteSourceUrlAccess(logStatisticsRq);
    }
    
    /**
     * 统计系统每日访问量
     * @param token
     * @param logIpRq
     * @return
     */
    @RequestMapping(value = "/webLog/countAccessByDay", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResultVo<List<LogStatisticsRs>> countAccessByDay(@RequestParam(required=true,value="token") String token,@RequestBody LogStatisticsRq logStatisticsRq){
        String tokenBak = MD5Util.MD5Encode(paramsConfig.getRestKey()+logStatisticsRq.getSiteSource());
        if(!tokenBak.equals(token)){// token 不匹配
            ResultVo<List<LogStatisticsRs>> resultVo = new ResultVo<>();
            resultVo.setMessage("token 不匹配");
            resultVo.setStatus(401);
            return resultVo;
        }
        return  webLogApi.statisticsDayAccess(logStatisticsRq);
    }
}
