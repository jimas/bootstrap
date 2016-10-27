package com.bootstrap.jimas.controller.web;
 
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootstrap.jimas.db.mongodb.dao.DemoInfoRepository;
import com.bootstrap.jimas.db.mongodb.domain.DemoInfo;
 
/**
 * @RestController  这个注解 回返回json对象
 * @author weqinjia.liu
 * @version v.0.1
 * @date 2016年8月18日下午8:49:35
 */
@Controller
public class DemoController {
    @Autowired
    private DemoInfoRepository demoInfoRepository;
   
    @RequestMapping("保存")
    @ResponseBody
    public String save(@RequestParam( required=false)String param){
       DemoInfo demoInfo = new DemoInfo();
       demoInfo.setName("张三");
       demoInfo.setAge(20);
       demoInfoRepository.save(demoInfo);
      
       demoInfo = new DemoInfo();
       demoInfo.setName("李四");
       demoInfo.setAge(30);
       demoInfoRepository.save(demoInfo);
      
       return "保存成功请问"+param;
    }
   
    @RequestMapping("find")
    @ResponseBody
    public List<DemoInfo> find(){
       return demoInfoRepository.findAll();
    }
   
    @RequestMapping("findByName")
    @ResponseBody
    public DemoInfo findByName(){
       return demoInfoRepository.findByName("张三");
    }
    @RequestMapping("/demo")
    public String demo(Model model){
       model.addAttribute("demo",new DemoInfo());
       return "/demo";
    }
   
    @RequestMapping("/demoAdd")
    public String demoAdd(@Valid DemoInfo demo,BindingResult result,Model model){
       //有错误信息.
       model.addAttribute("demo",demo);
//       result.rejectValue("name", "misFormat", "这个name已经注册过了！");
       if(result.hasErrors()){
           List<ObjectError>  list = result.getAllErrors();
           for(ObjectError  error:list){
           System.out.println(error.getCode()+"---"+error.getArguments()+"---"+error.getDefaultMessage());
           }
          
           return "/demo";
       }
       return "/demo";
    }
   
}