package com.creolophus.liuyi.common.api;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WebStart extends WebMvcConfigurationSupport  implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(WebStart.class);


//    /**
//     * 跨域
//     * @return
//     */
//    @Bean
//    public CorsFilter corsFilter() {
//        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        final CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.setMaxAge(3600L);
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsFilter(urlBasedCorsConfigurationSource);
//    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

    @Bean
    @ConditionalOnMissingBean
    public FastJsonConfig fastJsonConfig(){
        FastJsonConfig fjc = new FastJsonConfig();
        fjc.setSerializerFeatures(
                SerializerFeature.WriteDateUseDateFormat,//默认使用yyyy-mm-dd HH:mm:ss,也可以直接改
                SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse);
        fjc.setSerializeConfig(serializeConfig());
        fjc.setSerializeFilters(valueFilter());
        return fjc;
    }


    @Bean
    @ConditionalOnMissingBean
    public SerializeConfig serializeConfig(){
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(BigDecimal.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        return serializeConfig;
    }


    /**
     * fastJSON 序列化,Date 类型,如果为 null,返回""
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ValueFilter valueFilter(){
        return (Object var1, String var2, Object var3) -> {
            try {
                if(var3 == null && var1 != null && Date.class.isAssignableFrom(var1.getClass().getDeclaredField(var2).getType())) {
                    return "";
                }
            } catch (Exception e) {
            }
            return var3;
        };
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
        fastJsonConverter.setFastJsonConfig(fastJsonConfig());
        converters.add(fastJsonConverter);
        logger.info("start addMessageConverters with FastJsonHttpMessageConverter");
    }


    /**
     * 自己的业务走这里
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ApiInterceptor apiInterceptor = apiInterceptor();
        if(apiInterceptor!=null){
            logger.info("start addInterceptor with ApiInterceptor");
            registry.addInterceptor(apiInterceptor).addPathPatterns(apiInterceptor.getPathPatterns());
        }
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties(prefix="liuyi.global")
    public GlobalSetting globalSetting(){
        return new GlobalSetting();
    }

    /**
     * 此对象用来校验每个业务的请求中都必须含有的 header & 封装每个线程的 ApiContext
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ApiContextValidator apiContextValidator(){
        return new ApiContextValidator();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiContextFilter apiContextFilter() {
        return new ApiContextFilter();
    }

    /**
     * 如果ApiContextFilter和Spring Security Filter同时存在，那么先走SpringSecurityFilter
     * 实现Ordered接口，@Order都无效，只能通过如下方式指定顺序。
     * PS：Sleuth的TraceWebFilter的order是Ordered.HIGHEST_PRECEDENCE + 5，
     * 这样会打乱原油的顺序
     * @return
     */
//    @Bean
//    public FilterRegistrationBean<ApiContextFilter> registerLoginCheckFilter(ApiContextFilter apiContextFilter) {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(apiContextFilter);
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setName("apiContextFilter");
//        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return registrationBean;
//    }

    @Bean
    @ConditionalOnMissingBean
    public ApiInterceptor apiInterceptor(){
        return null;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
        logger.info("start addResourceHandler for swagger");
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        final RequestMappingHandlerAdapter bean = applicationContext.getBean(RequestMappingHandlerAdapter.class);
        List<HandlerMethodArgumentResolver> list = new ArrayList();
        list.add(new NullArgumentConfirm(false));
        list.addAll(bean.getArgumentResolvers());
        bean.setArgumentResolvers(list);
    }

}
