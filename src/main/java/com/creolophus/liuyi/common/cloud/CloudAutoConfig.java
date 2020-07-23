package com.creolophus.liuyi.common.cloud;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author magicnana
 * @date 2019/3/1 上午12:36
 */

@Configuration
@ConditionalOnClass(Feign.class)
public class CloudAutoConfig {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(CloudAutoConfig.class);

    /**
     * 每一次 feign 调用如果想增加些 header 或者通用参数,可以在这里
     * @return
     */
    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    public RequestInterceptor requestInterceptor(){
        return new CustomRequestInterceptor();
    }

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.NONE;
    }

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    public ErrorDecoder errorDecoder() {
        logger.info("start feign");
        return new CustomErrorDecoder();
    }

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    public Decoder decoder() {
        return new CustomDecoder();
    }

    @Bean
    @ConditionalOnClass(HystrixMetricsStreamServlet.class)
    @Scope("prototype")
    @ConditionalOnMissingBean
    public ServletRegistrationBean servletRegistrationBean(){
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }

}
