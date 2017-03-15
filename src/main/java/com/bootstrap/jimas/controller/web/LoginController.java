package com.bootstrap.jimas.controller.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootstrap.jimas.common.Constant;
import com.bootstrap.jimas.db.mongodb.domain.UserInfo;
import com.bootstrap.jimas.db.mongodb.service.UserInfoService;
import com.bootstrap.jimas.utils.CookieUtil;
import com.jimas.common.ResultVo;
import com.jimas.common.mail.MailUtil;
import com.jimas.common.util.RequestPathUtil;

import freemarker.template.TemplateException;

@Controller
public class LoginController {
    @Autowired
    private UserInfoService userInfoService;
    /**
     * 登录
     * @param map
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/userLogin")
    public String userLogin(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        map.addAttribute("userInfo", new UserInfo());
        return "pages/user_login";  
    }
    /**
     * 退出登录
     * @param map
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/LogOut",method=RequestMethod.GET)
    public String logOut(ModelMap map,HttpServletRequest request,HttpServletResponse response){
        //清除cookie
        CookieUtil.clearCookie(request, response, Constant.CURRENT_LOGIN_USER);
        return "redirect:/index";
    }
    /**
     * 跳转到 主页
     * @param userInfo
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/gotoJimas",method=RequestMethod.POST)
    public ResultVo<String> gotoJimas(UserInfo userInfo,HttpServletRequest request, HttpServletResponse response){
        ResultVo<String> resultVo = new ResultVo<String>();
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        userInfo = userInfoService.findByUsernameAndPassword(username, password);
        if(StringUtils.isEmpty(userInfo)){
            resultVo.setStatus(400);
            resultVo.setMessage("用户名密码错误");
            return resultVo;
        }
        CookieUtil.saveCookie(response, Constant.max_age, Constant.CURRENT_LOGIN_USER, username);
        return resultVo;
    }
    /**
     * 注册用户
     * @param userInfo
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/gotoRegister",method=RequestMethod.POST)
    public ResultVo<String> gotoRegister(UserInfo userInfo,HttpServletRequest request, HttpServletResponse response){
        ResultVo<String> resultVo = new ResultVo<String>();
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            resultVo.setStatus(401);
            resultVo.setMessage("用户名或密码不能为空");
            return resultVo;
        }
        UserInfo exitUser = userInfoService.findByUsername(username);
        if(!StringUtils.isEmpty(exitUser)){
            resultVo.setStatus(400);
            resultVo.setMessage("用户名已存在");
            return resultVo;
        }
        userInfo.setName(username);
        UserInfo saveUserInfo = userInfoService.saveUserInfo(userInfo);//新增用户
        CookieUtil.saveCookie(response, Constant.max_age, Constant.CURRENT_LOGIN_USER, saveUserInfo.getUsername());
        return resultVo;
    }
    /**
     * 找回密码
     * @param userInfo
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/gotoGetPwd",method=RequestMethod.POST)
    public ResultVo<String> gotoGetPwd(UserInfo userInfo,HttpServletRequest request, HttpServletResponse response){
        ResultVo<String> resultVo = new ResultVo<String>();
        String receiver = userInfo.getEmail();
        UserInfo findByEmail = userInfoService.findByEmail(receiver);
        if(StringUtils.isEmpty(findByEmail)){
            resultVo.setStatus(400);
            resultVo.setMessage("该邮箱未注册用户");
            return resultVo;
        }
        Map<String, String> map=new HashMap<String, String>();
        String homePath = RequestPathUtil.getHomePath(request);
        if (!homePath.endsWith("/")) {
            homePath += "/" ;
        }
        String href=homePath+"gotoGetUser?email="+receiver;
        String url="<a href=\""+href+"\">点击找回</a>";
        map.put("content", url);
        String templateName="template_1.ftl";
        String subject="找回密码";
        String maiBody="找回密码找回密码";
        try {
            MailUtil.sendMail(receiver, subject, maiBody);
            MailUtil.sendMailByTemplate(receiver,subject, map, templateName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return resultVo;
    }
    
    @RequestMapping("/gotoGetUser")
    public  @ResponseBody ResultVo<UserInfo>  findUserInfo(@RequestParam(value="email") String email){
        ResultVo<UserInfo> resultVo = new ResultVo<UserInfo>();
        UserInfo findByEmail = userInfoService.findByEmail(email);
        resultVo.setResult(findByEmail);
        return resultVo;
    }
}
