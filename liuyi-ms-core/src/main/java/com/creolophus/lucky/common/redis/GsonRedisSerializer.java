package com.creolophus.lucky.common.redis;

import com.creolophus.lucky.common.json.GsonUtil;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author magicnana
 * @date 2021/12/7 14:21
 */
public class GsonRedisSerializer implements RedisSerializer<Object> {

  @Override
  public byte[] serialize(Object o) throws SerializationException {
    return GsonUtil.toByteArray(o);
  }

  @Override
  public Object deserialize(byte[] bytes) throws SerializationException {
    return GsonUtil.toJava(bytes,Object.class);
  }
}
