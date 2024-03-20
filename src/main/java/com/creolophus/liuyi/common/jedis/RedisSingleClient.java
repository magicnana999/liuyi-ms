package com.creolophus.liuyi.common.jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

/**
 * @author magicnana
 * @date 2019/5/14 上午10:24
 */
public class RedisSingleClient implements RedisClient {

  private JedisPool jedisPool;

  public RedisSingleClient(JedisPool jedisPool) {
    this.jedisPool = jedisPool;
  }


  @Override
  public Set<String> evalSetString(String script, List<String> keys, List<String> args) {
    Jedis jedis = open();
    try {
      return (Set<String>) jedis.eval(script, keys, args);
    } finally {
      close(jedis);
    }
  }

  private void close(Jedis jedis) {
    try {
      if (jedis != null) {
        jedis.close();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private Jedis open() {
    Jedis jedis = jedisPool.getResource();
    jedis.select(Protocol.DEFAULT_DATABASE);
    return jedis;
  }


  @Override
  public List<String> mget(String... key) {
    Jedis jedis = open();
    try {
      return jedis.mget(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public boolean lockIn(String key, long expireSecond) {
    final String value = String.valueOf(System.currentTimeMillis());
    final String nxxx = "NX";
    final String expx = "EX";
    boolean ret;
    if (expireSecond <= 0L) {
      throw new RuntimeException("expire time could not be less than 0");
    } else {
      ret = "ok".equalsIgnoreCase(set(key, value, nxxx, expx, expireSecond));
    }
    return ret;
  }

  public boolean lock(String key) {
    final String value = String.valueOf(System.currentTimeMillis());
    String ret = set(key,value,"nx","ex",30);
    return StringUtils.isNoneBlank(ret);
  }

  @Override
  public Long publish(String channel, String message) {
    Jedis jedis = open();
    try {
      return jedis.publish(channel, message);
    } finally {
      close(jedis);
    }
  }

  @Override
  public void subscribe(JedisPubSub jedisPubSub, String... channels) {
    Jedis jedis = open();
    try {
      jedis.subscribe(jedisPubSub, channels);
    } finally {
      close(jedis);
    }

  }

  @Override
  public boolean unlock(final String key, final String keySign) {
    if (keySign != null && keySign.length() > 0) {
      String val = get(key);
      if (!keySign.equals(val)) {
        return true;
      }
    }
    if (del(key) <= 0) {
      if (exists(key)) {
        return false;
      } else {
        return true;
      }
    }
    return true;
  }

  @Override
  public List<String> scan(String pattern, int count) {
    Jedis jedis = open();
    try {
      List<String> list = new ArrayList<>();
      String cursor = ScanParams.SCAN_POINTER_START;
      ScanParams scanParams = new ScanParams();
      scanParams.count(count);
      scanParams.match(pattern);
      do {
        ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
        list.addAll(scanResult.getResult());
        cursor = scanResult.getStringCursor();
      } while (!"0".equals(cursor));

      return list;
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long del(String... keys) {
    Jedis jedis = open();
    try {
      return jedis.del(keys);
    } finally {
      close(jedis);
    }
  }


  @Override
  public String set(String key, String value) {
    Jedis jedis = open();
    try {
      return jedis.set(key, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String set(String key, String value, String nxxx, String expx, long time) {
    Jedis jedis = open();
    try {
      return jedis.set(key, value, nxxx, expx, time);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String set(String key, String value, String nxxx) {
    Jedis jedis = open();
    try {
      return jedis.set(key, value, nxxx);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String get(String key) {
    Jedis jedis = open();
    try {
      return jedis.get(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Boolean exists(String key) {
    Jedis jedis = open();
    try {
      return jedis.exists(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long persist(String key) {
    Jedis jedis = open();
    try {
      return jedis.persist(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String type(String key) {
    Jedis jedis = open();
    try {
      return jedis.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long expire(String key, int seconds) {
    Jedis jedis = open();
    try {
      return jedis.expire(key, seconds);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long pexpire(String key, long milliseconds) {

    Jedis jedis = open();
    try {
      return jedis.pexpire(key, milliseconds);

    } finally {
      close(jedis);
    }
  }

  @Override
  public Long expireAt(String key, long unixTime) {
    Jedis jedis = open();
    try {
      return jedis.expireAt(key, unixTime);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long pexpireAt(String key, long millisecondsTimestamp) {
    Jedis jedis = open();
    try {
      return jedis.pexpireAt(key, millisecondsTimestamp);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long ttl(String key) {
    Jedis jedis = open();
    try {

      return jedis.ttl(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long pttl(String key) {
    Jedis jedis = open();
    try {
      return jedis.pttl(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Boolean setbit(String key, long offset, boolean value) {
    Jedis jedis = open();
    try {
      return jedis.setbit(key, offset, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Boolean setbit(String key, long offset, String value) {
    Jedis jedis = open();
    try {
      return jedis.setbit(key, offset, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Boolean getbit(String key, long offset) {
    Jedis jedis = open();
    try {
      return jedis.getbit(key, offset);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long setrange(String key, long offset, String value) {
    Jedis jedis = open();
    try {
      return jedis.setrange(key, offset, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String getrange(String key, long startOffset, long endOffset) {
    Jedis jedis = open();
    try {
      return jedis.getrange(key, startOffset, endOffset);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String getSet(String key, String value) {
    Jedis jedis = open();
    try {
      return jedis.getSet(key, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long setnx(String key, String value) {
    Jedis jedis = open();
    try {
      return jedis.setnx(key, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String setex(String key, int seconds, String value) {
    Jedis jedis = open();
    try {
      return jedis.setex(value, seconds, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String psetex(String key, long milliseconds, String value) {
    Jedis jedis = open();
    try {
      return jedis.psetex(key, milliseconds, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long decrBy(String key, long integer) {
    Jedis jedis = open();
    try {
      return jedis.decrBy(key, integer);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long decr(String key) {
    Jedis jedis = open();
    try {
      return jedis.decr(key);

    } finally {
      close(jedis);
    }
  }

  @Override
  public Long incrBy(String key, long integer) {
    Jedis jedis = open();
    try {
      return jedis.incrBy(key, integer);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Double incrByFloat(String key, double value) {
    Jedis jedis = open();
    try {
      return jedis.incrByFloat(key, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long incr(String key) {
    Jedis jedis = open();
    try {
      return jedis.incr(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long append(String key, String value) {
    Jedis jedis = open();
    try {
      return jedis.append(key, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String substr(String key, int start, int end) {
    Jedis jedis = open();
    try {
      return jedis.substr(key, start, end);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long hset(String key, String field, String value) {
    Jedis jedis = open();
    try {
      return jedis.hset(key, field, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String hget(String key, String field) {
    Jedis jedis = open();
    try {
      return jedis.hget(key, field);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long hsetnx(String key, String field, String value) {
    Jedis jedis = open();
    try {
      return jedis.hsetnx(key, field, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String hmset(String key, Map<String, String> hash) {
    Jedis jedis = open();
    try {
      return jedis.hmset(key, hash);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<String> hmget(String key, String... fields) {
    Jedis jedis = open();
    try {
      return hmget(key, fields);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long hincrBy(String key, String field, long value) {
    Jedis jedis = open();
    try {
      return jedis.hincrBy(key, field, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Double hincrByFloat(String key, String field, double value) {
    Jedis jedis = open();
    try {
      return jedis.hincrByFloat(key, field, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Boolean hexists(String key, String field) {
    Jedis jedis = open();
    try {
      return jedis.hexists(key, field);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long hdel(String key, String... field) {
    Jedis jedis = open();
    try {
      return jedis.hdel(key, field);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long hlen(String key) {
    Jedis jedis = open();
    try {
      return jedis.hlen(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<String> hkeys(String key) {
    Jedis jedis = open();
    try {
      return jedis.hkeys(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<String> hvals(String key) {
    Jedis jedis = open();
    try {
      return jedis.hvals(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Map<String, String> hgetAll(String key) {
    Jedis jedis = open();
    try {
      return jedis.hgetAll(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long rpush(String key, String... string) {
    Jedis jedis = open();
    try {
      return jedis.rpush(key, string);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long lpush(String key, String... string) {
    Jedis jedis = open();
    try {
      return jedis.lpush(key, string);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long llen(String key) {
    Jedis jedis = open();
    try {
      return jedis.llen(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<String> lrange(String key, long start, long end) {
    Jedis jedis = open();
    try {
      return jedis.lrange(key, start, end);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String ltrim(String key, long start, long end) {
    Jedis jedis = open();
    try {
      return jedis.ltrim(key, start, end);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String lindex(String key, long index) {
    Jedis jedis = open();
    try {
      return jedis.lindex(key, index);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String lset(String key, long index, String value) {
    Jedis jedis = open();
    try {
      return jedis.lset(key, index, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long lrem(String key, long count, String value) {
    Jedis jedis = open();
    try {
      return jedis.lrem(key, count, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String lpop(String key) {
    Jedis jedis = open();
    try {
      return jedis.lpop(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String rpop(String key) {
    Jedis jedis = open();
    try {
      return jedis.rpop(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long sadd(String key, String... member) {
    Jedis jedis = open();
    try {
      return jedis.sadd(key, member);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<String> smembers(String key) {
    Jedis jedis = open();
    try {
      return jedis.smembers(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long srem(String key, String... member) {
    Jedis jedis = open();
    try {
      return jedis.srem(key, member);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String spop(String key) {
    Jedis jedis = open();
    try {
      return jedis.spop(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<String> spop(String key, long count) {
    Jedis jedis = open();
    try {
      return jedis.spop(key, count);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long scard(String key) {
    Jedis jedis = open();
    try {
      return jedis.scard(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Boolean sismember(String key, String member) {
    Jedis jedis = open();
    try {
      return jedis.sismember(key, member);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String srandmember(String key) {
    Jedis jedis = open();
    try {
      return jedis.srandmember(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<String> srandmember(String key, int count) {
    Jedis jedis = open();
    try {
      return jedis.srandmember(key, count);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long strlen(String key) {
    Jedis jedis = open();
    try {
      return jedis.strlen(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long zadd(String key, double score, String member) {
    Jedis jedis = open();
    try {
      return jedis.zadd(key, score, member);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long zadd(String key, double score, String member, ZAddParams params) {
    Jedis jedis = open();
    try {
      return jedis.zadd(key, score, member, params);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long zadd(String key, Map<String, Double> scoreMembers) {
    Jedis jedis = open();
    try {
      return jedis.zadd(key, scoreMembers);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
    Jedis jedis = open();
    try {
      return jedis.zadd(key, scoreMembers, params);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<String> zrange(String key, long start, long end) {
    Jedis jedis = open();
    try {
      return jedis.zrange(key, start, end);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long zrem(String key, String... member) {
    Jedis jedis = open();
    try {
      return jedis.zrem(key, member);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Double zincrby(String key, double score, String member) {
    Jedis jedis = open();
    try {
      return jedis.zincrby(key, score, member);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Double zincrby(String key, double score, String member, ZIncrByParams params) {
    Jedis jedis = open();
    try {
      return jedis.zincrby(key, score, member, params);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long zrank(String key, String member) {
    Jedis jedis = open();
    try {
      return jedis.zrank(key, member);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long zrevrank(String key, String member) {
    Jedis jedis = open();
    try {
      return jedis.zrevrank(key, member);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<String> zrevrange(String key, long start, long end) {
    Jedis jedis = open();
    try {
      return jedis.zrevrange(key, start, end);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<Tuple> zrangeWithScores(String key, long start, long end) {
    Jedis jedis = open();
    try {
      return jedis.zrangeWithScores(key, start, end);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
    Jedis jedis = open();
    try {
      return jedis.zrevrangeWithScores(key, start, end);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long zcard(String key) {
    Jedis jedis = open();
    try {
      return jedis.zcard(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Double zscore(String key, String member) {
    Jedis jedis = open();
    try {
      return jedis.zscore(key, member);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<String> sort(String key) {
    Jedis jedis = open();
    try {
      return jedis.sort(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<String> sort(String key, SortingParams sortingParameters) {
    Jedis jedis = open();
    try {
      return jedis.sort(key, sortingParameters);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long zcount(String key, double min, double max) {
    Jedis jedis = open();
    try {

      return jedis.zcount(key, min, max);

    } finally {
      close(jedis);
    }
  }

  @Override
  public Long zcount(String key, String min, String max) {
    Jedis jedis = open();
    try {
      return jedis.zcount(key, min, max);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<String> zrangeByScore(String key, double min, double max) {
    Jedis jedis = open();
    try {
      return zrangeByScore(key, min, max);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<String> zrangeByScore(String key, String min, String max) {
    Jedis jedis = open();
    try {
      return zrangeByScore(key, min, max);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<String> zrevrangeByScore(String key, double max, double min) {
    Jedis jedis = open();
    try {
      return jedis.zrevrangeByScore(key, max, min);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
    Jedis jedis = open();
    try {
      return jedis.zrangeByScore(key, min, max, offset, count);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<String> zrevrangeByScore(String key, String max, String min) {
    Jedis jedis = open();
    try {
      return jedis.zrevrangeByScore(key, max, min);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
    Jedis jedis = open();
    try {
      return jedis.zrangeByScore(key, min, max, offset, count);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
    Jedis jedis = open();
    try {
      return jedis.zrevrangeByScore(key, max, min, offset, count);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
    Jedis jedis = open();
    try {
      return jedis.zrangeByScoreWithScores(key, min, max);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
    Jedis jedis = open();
    try {
      return jedis.zrevrangeByScoreWithScores(key, max, min);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset,
      int count) {
    Jedis jedis = open();
    try {
      return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
    Jedis jedis = open();
    try {
      return jedis.zrevrangeByScore(key, max, min, offset, count);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
    Jedis jedis = open();
    try {
      return jedis.zrangeByScoreWithScores(key, min, max);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
    Jedis jedis = open();
    try {
      return jedis.zrevrangeByScoreWithScores(key, max, min);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset,
      int count) {
    Jedis jedis = open();
    try {
      return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset,
      int count) {
    Jedis jedis = open();
    try {
      return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset,
      int count) {
    Jedis jedis = open();
    try {
      return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long zremrangeByRank(String key, long start, long end) {
    Jedis jedis = open();
    try {
      return jedis.zremrangeByRank(key, start, end);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long zremrangeByScore(String key, double start, double end) {
    Jedis jedis = open();
    try {
      return jedis.zremrangeByScore(key, start, end);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long zremrangeByScore(String key, String start, String end) {
    Jedis jedis = open();
    try {
      return jedis.zremrangeByScore(key, start, end);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long zlexcount(String key, String min, String max) {
    Jedis jedis = open();
    try {
      return jedis.zlexcount(key, min, max);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<String> zrangeByLex(String key, String min, String max) {
    Jedis jedis = open();
    try {
      return jedis.zrangeByLex(key, min, max);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
    Jedis jedis = open();
    try {
      return jedis.zrangeByLex(key, min, max, offset, count);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<String> zrevrangeByLex(String key, String max, String min) {
    Jedis jedis = open();
    try {
      return jedis.zrevrangeByLex(key, max, min);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
    Jedis jedis = open();
    try {
      return jedis.zrevrangeByLex(key, max, min, offset, count);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long zremrangeByLex(String key, String min, String max) {
    Jedis jedis = open();
    try {
      return jedis.zremrangeByLex(key, min, max);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
    Jedis jedis = open();
    try {
      return jedis.linsert(key, where, pivot, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long lpushx(String key, String... string) {
    Jedis jedis = open();
    try {
      return jedis.lpushx(key, string);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long rpushx(String key, String... string) {
    Jedis jedis = open();
    try {
      return jedis.rpushx(key, string);
    } finally {
      close(jedis);
    }
  }


  @Override
  @Deprecated
  public List<String> blpop(String arg) {
    Jedis jedis = open();
    try {
      return jedis.blpop(arg);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<String> blpop(int timeout, String key) {
    Jedis jedis = open();
    try {
      return jedis.blpop(timeout, key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<String> brpop(String arg) {
    Jedis jedis = open();
    try {
      return jedis.brpop(arg);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<String> brpop(int timeout, String key) {
    Jedis jedis = open();
    try {
      return jedis.brpop(timeout, key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long del(String key) {
    Jedis jedis = open();
    try {
      return jedis.del(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String echo(String string) {
    Jedis jedis = open();
    try {
      return jedis.echo(string);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long move(String key, int dbIndex) {
    Jedis jedis = open();
    try {
      return jedis.move(key, dbIndex);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long bitcount(String key) {
    Jedis jedis = open();
    try {
      return jedis.bitcount(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long bitcount(String key, long start, long end) {
    Jedis jedis = open();
    try {
      return jedis.bitcount(key, start, end);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long bitpos(String key, boolean value) {
    Jedis jedis = open();
    try {
      return jedis.bitpos(key, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long bitpos(String key, boolean value, BitPosParams params) {
    Jedis jedis = open();
    try {
      return jedis.bitpos(key, value, params);
    } finally {
      close(jedis);
    }
  }

  @Override
  public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) {
    Jedis jedis = open();
    try {
      return jedis.hscan(key, cursor);
    } finally {
      close(jedis);
    }
  }

  @Override
  public ScanResult<String> sscan(String key, int cursor) {
    Jedis jedis = open();
    try {
      return jedis.sscan(key, cursor);
    } finally {
      close(jedis);
    }
  }

  @Override
  public ScanResult<Tuple> zscan(String key, int cursor) {
    Jedis jedis = open();
    try {
      return jedis.zscan(key, cursor);
    } finally {
      close(jedis);
    }
  }

  @Override
  public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
    Jedis jedis = open();
    try {
      return jedis.hscan(key, cursor);
    } finally {
      close(jedis);
    }
  }

  @Override
  public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
    Jedis jedis = open();
    try {
      return jedis.hscan(key, cursor, params);
    } finally {
      close(jedis);
    }
  }

  @Override
  public ScanResult<String> sscan(String key, String cursor) {
    Jedis jedis = open();
    try {
      return jedis.sscan(key, cursor);
    } finally {
      close(jedis);
    }
  }

  @Override
  public ScanResult<String> sscan(String key, String cursor, ScanParams params) {
    Jedis jedis = open();
    try {
      return jedis.sscan(key, cursor, params);
    } finally {
      close(jedis);
    }
  }

  @Override
  public ScanResult<Tuple> zscan(String key, String cursor) {
    Jedis jedis = open();
    try {
      return jedis.zscan(key, cursor);
    } finally {
      close(jedis);
    }
  }

  @Override
  public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
    Jedis jedis = open();
    try {
      return jedis.zscan(key, cursor, params);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long pfadd(String key, String... elements) {
    Jedis jedis = open();
    try {
      return jedis.pfadd(key, elements);
    } finally {
      close(jedis);
    }
  }

  @Override
  public long pfcount(String key) {
    Jedis jedis = open();
    try {
      return jedis.pfadd(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long geoadd(String key, double longitude, double latitude, String member) {
    Jedis jedis = open();
    try {
      return jedis.geoadd(key, longitude, latitude, member);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
    Jedis jedis = open();
    try {
      return jedis.geoadd(key, memberCoordinateMap);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Double geodist(String key, String member1, String member2) {
    Jedis jedis = open();
    try {
      return jedis.geodist(key, member1, member2);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Double geodist(String key, String member1, String member2, GeoUnit unit) {
    Jedis jedis = open();
    try {
      return jedis.geodist(key, member1, member2, unit);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<String> geohash(String key, String... members) {
    Jedis jedis = open();
    try {
      return jedis.geohash(key, members);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<GeoCoordinate> geopos(String key, String... members) {
    Jedis jedis = open();
    try {
      return jedis.geopos(key, members);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude,
      double radius, GeoUnit unit) {
    Jedis jedis = open();
    try {
      return jedis.georadius(key, longitude, latitude, radius, unit);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude,
      double radius, GeoUnit unit, GeoRadiusParam param) {
    Jedis jedis = open();
    try {
      return jedis.georadius(key, longitude, latitude, radius, unit, param);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius,
      GeoUnit unit) {
    Jedis jedis = open();
    try {
      return jedis.georadiusByMember(key, member, radius, unit);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius,
      GeoUnit unit, GeoRadiusParam param) {
    Jedis jedis = open();
    try {
      return jedis.georadiusByMember(key, member, radius, unit, param);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<Long> bitfield(String key, String... arguments) {
    Jedis jedis = open();
    try {
      return jedis.bitfield(key, arguments);
    } finally {
      close(jedis);
    }
  }

}
