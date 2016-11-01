package com.bootstrap.jimas.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActionTokenModel {
	//装载页面时调用save=true
    boolean save() default false;
    //保存时调用remove=true
    boolean remove() default false;
}