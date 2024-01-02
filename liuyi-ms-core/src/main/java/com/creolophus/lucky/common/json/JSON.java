package com.creolophus.lucky.common.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.util.DigestUtils;

/**
 * @author magicnana
 * @date 2020/8/17 1:51 PM
 */
public class JSON {

  public static <T> T parseObject(String json, Type type) {
    return GsonUtil.toJava(json, type);
  }

  public static <T> T parseObject(String json, Class<T> clazz) {
    return GsonUtil.toJava(json, clazz);
  }

  public static <T> T parseObject(Object object, Type type) {
    return GsonUtil.toJava(object, type);
  }

  public static <T> T parseObject(Object object, Class<T> clazz) {
    return GsonUtil.toJava(object, clazz);
  }

  public static <T> T parseObject(byte[] bytes, Class<T> clazz) {
    return GsonUtil.toJava(bytes, clazz);
  }

  public static <T> T parseObject(byte[] bytes, Type type) {
    return GsonUtil.toJava(bytes, type);
  }

  public static String toJSONString(Object object) {
    return GsonUtil.toJson(object);
  }

  public static byte[] toJSONBytes(Object object) {
    return GsonUtil.toByteArray(object);
  }


  public static void main(String[] args){
    List<Integer> list = Arrays.asList(100,200);
    String json = toJSONString(list);
    System.out.println(json);

    Object obj = GsonUtil.toJava(json,new TypeToken<List<Integer>>(){}.getType());
    System.out.println(obj);

    Object obj2 = JacksonUtil.toJava(json,new TypeReference<List<Integer>>(){});
    System.out.println(obj2);

    Object obj3 = parseObject(json,new TypeReference<List<Integer>>(){}.getType());
    System.out.println(obj3);

    List<Long> list2 = Arrays.asList(3331341356795871232L);
    System.out.println(toJSONString(list2));


    String newPassword = DigestUtils.md5DigestAsHex(("953009" + "111111").getBytes());
    System.out.println(newPassword);


  }
}
