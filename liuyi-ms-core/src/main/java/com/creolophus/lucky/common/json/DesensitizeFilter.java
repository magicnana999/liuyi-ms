package com.creolophus.lucky.common.json;

import com.alibaba.fastjson.serializer.ValueFilter;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author magicnana
 * @date 2022/3/10 14:15
 */
public class DesensitizeFilter implements ValueFilter {

  public static final ConcurrentHashMap<String, Field> map = new ConcurrentHashMap();

  @Override
  public Object process(Object object, String name, Object value) {
    if (null == value || !(value instanceof String) || ((String) value).length() == 0) {
      return value;
    }
    try {

      String key = object.getClass().getName() + "-" + name;
      Field field = map.get(key);
      if (field == null) {
        field = object.getClass().getDeclaredField(name);
        if (field == null) {
          return value;
        }
        map.put(key, field);
      }

      Desensitized desensitization;
      if (String.class != field.getType()
          || (desensitization = field.getAnnotation(Desensitized.class)) == null) {
        return value;
      }

      switch (desensitization.type()) {
        case SHORTEN:
          return "JINSONG WISHES";
        default:
          return value;
      }

    } catch (Throwable e) {
      return value;
    }
  }
}
