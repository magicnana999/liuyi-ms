package com.creolophus.liuyi.common.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * @author magicnana
 * @date 2021/8/9 17:34
 */
@Configuration
public class MessageConvertAutoConfig {

  private static final Logger logger = LoggerFactory.getLogger(MessageConvertAutoConfig.class);


  @Bean
  public HttpMessageConverters fastJsonHttpMessageConverters() {

    /**
     * JacksonMessageConverter
     */
    MappingJackson2HttpMessageConverter jackson2HttpMessageConverter =
        new LiuyiMappingJackson2HttpMessageConverter();
    ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
    simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
    objectMapper.registerModule(simpleModule);
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addSerializer(
        LocalDateTime.class,
        new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    javaTimeModule.addDeserializer(
        LocalDateTime.class,
        new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    objectMapper.registerModule(javaTimeModule);
    jackson2HttpMessageConverter.setObjectMapper(objectMapper);

    /**
     * GSonMessageConverter
     */
    GsonHttpMessageConverter gsonHttpMessageConverter = new LiuyiGsonHttpMessageConverter();
    Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    gsonHttpMessageConverter.setGson(gson);

    if (logger.isInfoEnabled()) {
      logger.info("start -> addMessageConverters Jackson2HttpMessageConverter");
      logger.info("start -> addMessageConverters GSonHttpMessageConverter");
    }

    return new HttpMessageConverters(jackson2HttpMessageConverter,gsonHttpMessageConverter);
  }
}
