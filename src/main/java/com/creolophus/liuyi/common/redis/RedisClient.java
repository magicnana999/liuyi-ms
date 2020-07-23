package com.creolophus.liuyi.common.redis;


import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPubSub;

import java.util.List;
import java.util.Set;

/**
 * @author magicnana
 * @date 2019/5/14 上午10:37
 */
public interface RedisClient extends JedisCommands {

	int delMutiWithKeys(String prefix);

	Set<String> evalSetString(String script, List<String> keys, List<String> args);


	List<String> mget(String... key);

	/**
	 * lock
	 * @param key
	 * @param expireSecond
	 * @return
	 */
	public String lock(String key, long expireSecond);

	/**
	 * lock
	 * @param key
	 * @param expireMilisecond
	 * @return
	 */
	public String lockPx(String key, long expireMilisecond);

	Long publish(String channel, String message);

	void subscribe(JedisPubSub jedisPubSub, String... channels);

    /**
	 * unlock 
	 * @param key
	 * @param keySign
	 * @return
	 */
    boolean unlock(final String key, final String keySign);


    List<String> scan(String pattern, int count);

	Long del(String... keys);

}
