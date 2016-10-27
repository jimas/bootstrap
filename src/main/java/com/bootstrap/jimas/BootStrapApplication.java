package com.bootstrap.jimas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Description 关闭自动部署 设置 spring.devtools.restart.enabled 属性为false，可以关闭该特性。
 * 可以在application.properties中设置，也可以通过设置环境变量的方式。
 * @author weqinjia.liu
 * @Date 2016年10月14日
 */

@SpringBootApplication(scanBasePackages={"com.bootstrap.jimas"})
@PropertySources({ @PropertySource("classpath:config.properties")})
//@ImportResource({ "classpath:spring/applicationContext-dubbo.xml" })
@EnableAsync // 为了让@Async注解能够生效 (异步调用)
public class BootStrapApplication {
    private static final Logger logger = LoggerFactory.getLogger(BootStrapApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BootStrapApplication.class, args);
        //关闭自动部署
//        System.setProperty("spring.devtools.restart.enabled","false");
        logger.info(BootStrapApplication.class.getName()+ " =========  Start =========== ");
    }

}
