package com.bootstrap.jimas.controller.web;

import java.util.List;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bootstrap.jimas.interceptor.MenuModel;
import com.jimas.common.ResultVo;
import com.jimas.common.constant.ResultStatusEnum;
import com.jimas.common.util.ResultUtil;
import com.jimas.weixin.skytrap.repository.api.MenuButtonApi;
import com.jimas.weixin.skytrap.repository.api.request.MenuButtonReq;
import com.jimas.weixin.skytrap.repository.entity.MenuButton;
/**
 * @Description 菜单控制类
 * @author weqinjia.liu
 * @Date 2016年12月28日
 */
@Controller
public class MenuController {

    private static final Logger logger=Logger.getLogger(MenuController.class);
    
    @Autowired
    private MenuButtonApi menuButtonApi;
    @RequestMapping("showMenuList")
    @MenuModel
    public String showMenuList(ModelMap map,HttpRequest request,HttpResponse response){
    
        MenuButtonReq req=new MenuButtonReq();
        List<MenuButton> menuList = menuButtonApi.findList(req);
        map.put("menuList", menuList);

        return "pages/menu/menu_list";
    }
    @RequestMapping("saveMenu")
    @ResponseBody
    public ResultVo<Object> saveMenu(@ModelAttribute MenuButton menuButton,HttpRequest request,HttpResponse response){
        ResultVo<Object> resultVo = new ResultVo<Object>();
        try {
            boolean save = menuButtonApi.saveMenuButton(menuButton);
            if(!save){
                return ResultUtil.initResultVo(ResultStatusEnum.SERVICE_EXCEPTION.getStatus(),"保存失败",null);
            }
        } catch (Exception e) {
            logger.error("menuButtonApi.saveMenuButton", e);
            return ResultUtil.initResultVo(ResultStatusEnum.SERVICE_EXCEPTION.getStatus());
        }
        return resultVo;
    }
}
