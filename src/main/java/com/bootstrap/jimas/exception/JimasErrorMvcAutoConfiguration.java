package com.bootstrap.jimas.exception;

import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.ApplicationContext;
/**
 * @Description 暂时没有用到
 * @author weqinjia.liu
 * @Date 2016年10月14日
 */
public class JimasErrorMvcAutoConfiguration extends ErrorMvcAutoConfiguration implements EmbeddedServletContainerCustomizer {

    public JimasErrorMvcAutoConfiguration(ApplicationContext applicationContext, ServerProperties serverProperties,
            ResourceProperties resourceProperties) {
        super(applicationContext, serverProperties, resourceProperties);
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer arg0) {
        String path="";
        ErrorPage paramArrayOfErrorPage=new ErrorPage(path);
        arg0.addErrorPages(paramArrayOfErrorPage);
    }

}
