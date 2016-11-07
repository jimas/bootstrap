package com.bootstrap.jimas.db.mongodb.domain;

import org.springframework.data.annotation.Id;

public class UserInfo extends BaseDomain {

    private static final long serialVersionUID = -1334642569313949599L;

    @Id 
    private String id;//用户id;
   
    private String username;//账号.
   
    private String name;//名称（昵称或者真实姓名，不同系统不同定义）
   
    private String password; //密码;
    
    private String salt;//加密密码的盐
    
    private String email;//邮箱
   
    private char state;//用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public String getCredentialsSalt() {
        
        return this.username+this.salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    
    
}
