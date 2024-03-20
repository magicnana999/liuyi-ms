package com.creolophus.liuyi.common.jedis;


import java.util.List;
import java.util.Set;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPubSub;

/**
 * @author magicnana
 * @date 2019/5/14 上午10:37
 */
public interface RedisClient extends JedisCommands {

  Set<String> evalSetString(String script, List<String> keys, List<String> args);


  List<String> mget(String... key);


  /** 加锁，锁的有效期取决于 expire。加锁失败立即返回 */
  public boolean lockIn(String key, long second);

  /** 加锁，锁的有效期是永远有效（除非显示的删除）。加锁失败立即返回。 */
  public boolean lock(String key);


  Long publish(String channel, String message);

  void subscribe(JedisPubSub jedisPubSub, String... channels);

  /**
   * unlock
   */
  boolean unlock(final String key, final String keySign);


  List<String> scan(String pattern, int count);

  Long del(String... keys);

}
