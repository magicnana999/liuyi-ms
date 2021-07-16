package com.creolophus.liuyi.common.redis;


import java.util.List;
import java.util.Set;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPubSub;

/**
 * @author magicnana
 * @date 2019/5/14 上午10:37
 */
public interface RedisClient extends JedisCommands {

  Long del(String... keys);

  int delMutiWithKeys(String prefix);

  Set<String> evalSetString(String script, List<String> keys, List<String> args);

  /**
   * lock
   */
  public String lock(String key, long expireSecond);

  /**
   * lock
   */
  public String lockPx(String key, long expireMilisecond);

  List<String> mget(String... key);

  Long publish(String channel, String message);

  List<String> scan(String pattern, int count);

  void subscribe(JedisPubSub jedisPubSub, String... channels);

  /**
   * unlock
   */
  boolean unlock(final String key, final String keySign);

}
