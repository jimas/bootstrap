package com.bootstrap.jimas.db.mongodb.domain;
 
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
 
/**
 * @Description 实体类
 * @author weqinjia.liu
 * @Date 2016年9月29日
 */
public class DemoInfo extends BaseDomain {
   
    private static final long serialVersionUID = 3908548307404000386L;

    //id属性是给mongodb用的，用@Id注解修饰
    @Id
    private String id;
    @NotEmpty(message="姓名不能为空")
    private String name;
    
    @NotEmpty(message="密码不能为空")
    @Length(min=6,message="密码长度不能小于6位")
    private String password;
    
    @Max(value = 100, message = "年龄不能大于100岁")
    @Min(value= 18 ,message= "必须年满18岁！" )
    private int age;
    
    @Email(message = "比如输入正确的邮箱")
    private String email;
 
    public String getName() {
       return name;
    }
 
    public void setName(String name) {
       this.name = name;
    }
 
    public int getAge() {
       return age;
    }
 
    public void setAge(int age) {
       this.age = age;
    }
 
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}