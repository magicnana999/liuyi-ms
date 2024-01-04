package com.creolophus.common.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.StringUtils;

/**
 * 默认不忽略 Null 属性,但是这里已配置 SimpleDateFormat已指定 Object 或者泛型属性 有可能是 LinkedHashMap Object 或者泛型属性,如果制定
 * TypeReference或者 JavaType,那么结果是目标类型 new TypeReference<Pager<User>>(){} 这里还有一个把 LinkedHashMap 转为
 * JavaObject 的方法
 *
 * @author magicnana
 * @date 2020/8/19 9:15 AM
 */
public class JacksonUtil {

  private static ObjectMapper objectMapper;

  public static ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  public static ObjectMapper init() {

    if (objectMapper != null) {
      return objectMapper;
    }

    objectMapper = new ObjectMapper();

    // 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
    // Include.Include.ALWAYS 默认
    // Include.NON_DEFAULT 属性为默认值不序列化
    // Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
    // Include.NON_NULL 属性为NULL 不序列化
//    objectMapper.setSerializationInclusion(Include.NON_EMPTY);
    objectMapper.setSerializationInclusion(Include.NON_NULL);
//    objectMapper.setSerializationInclusion(Include.NON_DEFAULT);

    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
    simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

    simpleModule.addDeserializer(
        String.class,
        new StdDeserializer<String>(String.class) {
          @Override
          public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String result = StringDeserializer.instance.deserialize(p, ctxt);
            if (StringUtils.isEmpty(result)) {
              return null;
            }
            return result;
          }
        });

    objectMapper.registerModule(simpleModule);

    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addSerializer(
        LocalDateTime.class,
        new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    javaTimeModule.addDeserializer(
        LocalDateTime.class,
        new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    objectMapper.registerModule(javaTimeModule);

    return objectMapper;
  }

  static {
    objectMapper = init();
  }

  public static byte[] toByteArray(Object obj) {
    if (obj == null) {
      return null;
    }
    try {
      return objectMapper.writeValueAsBytes(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T toJava(byte[] bytes, Class<T> clazz) {
    if (bytes == null) {
      return null;
    }
    try {
      return objectMapper.readValue(bytes, clazz);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T toJava(String string, TypeReference<T> type) {
    if (null == string || "".equals(string)) {
      return null;
    }

    try {
      return objectMapper.readValue(string, type);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T toJava(byte[] bytes, TypeReference<T> type) {
    if (bytes == null) {
      return null;
    }
    try {
      return objectMapper.readValue(bytes, type);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T toJava(String string, JavaType type) {
    if (null == string || "".equals(string)) {
      return null;
    }
    try {
      return objectMapper.readValue(string, type);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T toJava(byte[] bytes, JavaType type) {
    if (bytes == null) {
      return null;
    }
    try {
      return objectMapper.readValue(bytes, type);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T toJava(String string, Class<T> clazz) {
    if (null == string || "".equals(string)) {
      return null;
    }

    try {
      return objectMapper.readValue(string, clazz);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T toJava(Object object, Class<T> clazz) {
    if (object == null) {
      return null;
    }
    return objectMapper.convertValue(object, clazz);
  }

  public static String toJson(Object obj) {
    if (obj == null) {
      return null;
    }
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
