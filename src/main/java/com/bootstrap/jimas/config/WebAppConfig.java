package com.bootstrap.jimas.config;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.bootstrap.jimas.interceptor.ResultMvcInterceptor;
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Value("${result.mvcInterceptor.add.path}")
    private String[] mvcInterceptorAddPath;
    @Value("${result.mvcInterceptor.exclude.path}")
    private String[] mvcInterceptorExcludePath;
    @Autowired
    private ResultMvcInterceptor resultMvcInterceptor;
    /**
     * 可以通过此方法添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(resultMvcInterceptor).addPathPatterns(mvcInterceptorAddPath).excludePathPatterns(mvcInterceptorExcludePath);//拦截的url
//        registry.addInterceptor(localeChangeInterceptor());  
        super.addInterceptors(registry);
    }

    /**
     * 格式化转换
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
//        registry.addFormatterForFieldType(LocalDate.class, new US);
        super.addFormatters(registry);
    }
    
    /*===================================================================================================*/
    @Bean
    public LocaleChangeInterceptor  localeChangeInterceptor (){
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    } 

    //name="localeResolver" 是必须的
    @Bean(name="localeResolver")
    public LocaleResolver localeResolverBean() {
        return new CookieLocaleResolver();
    }
    //则可以修改properties的前缀部分，name="messageSource" 同样是必须的
    @Bean(name="messageSource")
    public ResourceBundleMessageSource resourceBundleMessageSource(){
        ResourceBundleMessageSource source=new ResourceBundleMessageSource();
        source.setBasenames(new String[]{"i18n/resource","i18n/menu"});
        return source;
    }
//    @Bean  //暂时注释掉 未解决
    public EmbeddedServletContainerCustomizer containerCustomizer() {
      return new EmbeddedServletContainerCustomizer() {
        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
          container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
          container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
        }
      };
    }
//    @Bean
    public DefaultErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes (RequestAttributes requestAttributes,
            boolean includeStackTrace){
                Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
                errorAttributes.remove("error");
                errorAttributes.remove("exception");
                return errorAttributes;
            }
        };
    }
}
