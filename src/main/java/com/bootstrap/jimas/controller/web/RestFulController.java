package com.bootstrap.jimas.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bootstrap.jimas.api.MenuResApi;
import com.bootstrap.jimas.common.ResultVo;
import com.bootstrap.jimas.db.mongodb.response.MenuRes;
import com.bootstrap.jimas.utils.GsonUtil;
import com.bootstrap.jimas.utils.MD5Util;

/**
 * @Description restful 接口
 * @author weqinjia.liu
 * @Date 2017年3月13日
 */
@RestController
@RequestMapping("/rest")
public class RestFulController {
    
    @Value("${rest.key}")
    private String restKey;
    @Autowired
    private MenuResApi menuResApi;
    /**
     * 保存菜单对象
     * @param req
     * @return
     */
    @RequestMapping(value = "/menu/saveMenu", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResultVo saveMenu(@RequestParam(required=true,value="token") String token,@RequestBody MenuRes menuRes){
        String tokenBak = MD5Util.MD5Encode(restKey+menuRes.getSiteSource());
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
    public ResultVo queryMenu(@RequestParam(required=true,value="token") String token,@RequestBody String siteSource){
        
        String source = (String) GsonUtil.parseJson(siteSource, String.class);
        String tokenBak = MD5Util.MD5Encode(restKey+source);
        if(!tokenBak.equals(token)){// token 不匹配
            ResultVo resultVo = new ResultVo();
            resultVo.setMessage("token 不匹配");
            resultVo.setStatus(401);
            return resultVo;
        }
        return menuResApi.findMenuBySiteSource(source);
    }
    

}
