package com.creolophus.liuyi.common.jedis;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import redis.clients.jedis.AccessControlUser;
import redis.clients.jedis.Client;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.StreamConsumersInfo;
import redis.clients.jedis.StreamGroupInfo;
import redis.clients.jedis.StreamInfo;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.SetParams;

/**
 * @author magicnana
 * @date 2019/5/14 上午10:24
 */
public class JedisSingleClient extends Jedis {

  private JedisPool jedisPool;

  public JedisSingleClient(JedisPool jedisPool) {
    this.jedisPool = jedisPool;
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


  public String set(byte[] key, byte[] value) {
    Jedis jedis = null;
    try {
      jedis = open();
      return super.set(key, value);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String set(byte[] key, byte[] value, SetParams params) {
    Jedis jedis = null;
    try {
      jedis = open();
      return super.set(key, value, params);
    } finally {
      close(jedis);
    }
  }

  @Override
  public byte[] get(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = open();
      return super.get(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String quit() {
    Jedis jedis = null;
    try {
      jedis = open();
      return super.quit();
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long exists(byte[]... keys) {
    Jedis jedis = null;
    try {
      jedis = open();
      return super.exists(keys);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Boolean exists(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = open();
      return super.exists(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long del(byte[]... keys) {
    Jedis jedis = null;
    try {
      jedis = open();
      return super.del(keys);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long del(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = open();
      return super.del(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long unlink(byte[]... keys) {
    Jedis jedis = null;
    try {
      jedis = open();
      return super.unlink(keys);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long unlink(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = open();
      return super.unlink(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String type(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String flushDB() {
    Jedis jedis = null;
    try {
      jedis = open();
    return super.flushDB();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<byte[]> keys(byte[] pattern) {
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    return super.keys(pattern);
    } finally {
      close(jedis);
    }
  }

  @Override
  public byte[] randomBinaryKey() {
    Jedis jedis = null;
    try {
      jedis = open();
    return super.randomBinaryKey();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String rename(byte[] oldkey, byte[] newkey) {
    Jedis jedis = null;
    try {
      jedis = open();
    return super.rename(oldkey, newkey);
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long renamenx(byte[] oldkey, byte[] newkey) {
    Jedis jedis = null;
    try {
      jedis = open();
    return super.renamenx(oldkey, newkey);
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long dbSize() {
    Jedis jedis = null;
    try {
      jedis = open();
    return super.dbSize();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long expire(byte[] key, int seconds) {
    Jedis jedis = null;
    try {
      jedis = open();
    return super.expire(key, seconds);
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long expireAt(byte[] key, long unixTime) {
    Jedis jedis = null;
    try {
      jedis = open();
    return super.expireAt(key, unixTime);
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long ttl(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = open();
    return super.ttl(key);
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long touch(byte[]... keys) {
    Jedis jedis = null;
    try {
      jedis = open();
    return super.touch(keys);
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long touch(byte[] key) {
    return super.touch(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String select(int index) {
    return super.select(index);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String swapDB(int index1, int index2) {
    return super.swapDB(index1, index2);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long move(byte[] key, int dbIndex) {
    return super.move(key, dbIndex);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String flushAll() {
    return super.flushAll();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public byte[] getSet(byte[] key, byte[] value) {
    return super.getSet(key, value);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<byte[]> mget(byte[]... keys) {
    return super.mget(keys);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long setnx(byte[] key, byte[] value) {
    return super.setnx(key, value);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String setex(byte[] key, int seconds, byte[] value) {
    return super.setex(key, seconds, value);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String mset(byte[]... keysvalues) {
    return super.mset(keysvalues);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long msetnx(byte[]... keysvalues) {
    return super.msetnx(keysvalues);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long decrBy(byte[] key, long decrement) {
    return super.decrBy(key, decrement);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long decr(byte[] key) {
    return super.decr(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long incrBy(byte[] key, long increment) {
    return super.incrBy(key, increment);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Double incrByFloat(byte[] key, double increment) {
    return super.incrByFloat(key, increment);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long incr(byte[] key) {
    return super.incr(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long append(byte[] key, byte[] value) {
    return super.append(key, value);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public byte[] substr(byte[] key, int start, int end) {
    return super.substr(key, start, end);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long hset(byte[] key, byte[] field, byte[] value) {
    return super.hset(key, field, value);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long hset(byte[] key, Map<byte[], byte[]> hash) {
    return super.hset(key, hash);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public byte[] hget(byte[] key, byte[] field) {
    return super.hget(key, field);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long hsetnx(byte[] key, byte[] field, byte[] value) {
    return super.hsetnx(key, field, value);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String hmset(byte[] key, Map<byte[], byte[]> hash) {
    return super.hmset(key, hash);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<byte[]> hmget(byte[] key, byte[]... fields) {
    return super.hmget(key, fields);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long hincrBy(byte[] key, byte[] field, long value) {
    return super.hincrBy(key, field, value);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Double hincrByFloat(byte[] key, byte[] field, double value) {
    return super.hincrByFloat(key, field, value);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Boolean hexists(byte[] key, byte[] field) {
    return super.hexists(key, field);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long hdel(byte[] key, byte[]... fields) {
    return super.hdel(key, fields);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long hlen(byte[] key) {
    return super.hlen(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<byte[]> hkeys(byte[] key) {
    return super.hkeys(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<byte[]> hvals(byte[] key) {
    return super.hvals(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Map<byte[], byte[]> hgetAll(byte[] key) {
    return super.hgetAll(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long rpush(byte[] key, byte[]... strings) {
    return super.rpush(key, strings);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long lpush(byte[] key, byte[]... strings) {
    return super.lpush(key, strings);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long llen(byte[] key) {
    return super.llen(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public List<byte[]> lrange(byte[] key, long start, long stop) {
    return super.lrange(key, start, stop);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String ltrim(byte[] key, long start, long stop) {
    return super.ltrim(key, start, stop);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public byte[] lindex(byte[] key, long index) {
    return super.lindex(key, index);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public String lset(byte[] key, long index, byte[] value) {
    return super.lset(key, index, value);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long lrem(byte[] key, long count, byte[] value) {
    return super.lrem(key, count, value);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public byte[] lpop(byte[] key) {
    return super.lpop(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public byte[] rpop(byte[] key) {
    return super.rpop(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public byte[] rpoplpush(byte[] srckey, byte[] dstkey) {
    return super.rpoplpush(srckey, dstkey);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long sadd(byte[] key, byte[]... members) {
    return super.sadd(key, members);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<byte[]> smembers(byte[] key) {
    return super.smembers(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long srem(byte[] key, byte[]... member) {
    return super.srem(key, member);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public byte[] spop(byte[] key) {
    return super.spop(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Set<byte[]> spop(byte[] key, long count) {
    return super.spop(key, count);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long smove(byte[] srckey, byte[] dstkey, byte[] member) {
    return super.smove(srckey, dstkey, member);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
  }

  @Override
  public Long scard(byte[] key) {
    return super.scard(key)Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }

  @Override
  public Boolean sismember(byte[] key, byte[] member) {
    return super.sismember(key, member);
Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<byte[]> sinter(byte[]... keys) {
    return super.sinter(keys);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long sinterstore(byte[] dstkey, byte[]... keys) {
    return super.sinterstore(dstkey, keys);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<byte[]> sunion(byte[]... keys) {
    return super.sunion(keys);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long sunionstore(byte[] dstkey, byte[]... keys) {
    return super.sunionstore(dstkey, keys);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<byte[]> sdiff(byte[]... keys) {
    return super.sdiff(keys);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long sdiffstore(byte[] dstkey, byte[]... keys) {
    return super.sdiffstore(dstkey, keys);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public byte[] srandmember(byte[] key) {
    return super.srandmember(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> srandmember(byte[] key, int count) {
    return super.srandmember(key, count);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zadd(byte[] key, double score, byte[] member) {
    return super.zadd(key, score, member);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zadd(byte[] key, double score, byte[] member, ZAddParams params) {
    return super.zadd(key, score, member, params);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zadd(byte[] key, Map<byte[], Double> scoreMembers) {
    return super.zadd(key, scoreMembers);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zadd(byte[] key, Map<byte[], Double> scoreMembers, ZAddParams params) {
    return super.zadd(key, scoreMembers, params);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<byte[]> zrange(byte[] key, long start, long stop) {
    return super.zrange(key, start, stop);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zrem(byte[] key, byte[]... members) {
    return super.zrem(key, members);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Double zincrby(byte[] key, double increment, byte[] member) {
    return super.zincrby(key, increment, member);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Double zincrby(byte[] key, double increment, byte[] member, ZIncrByParams params) {
    return super.zincrby(key, increment, member, params);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zrank(byte[] key, byte[] member) {
    return super.zrank(key, member);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zrevrank(byte[] key, byte[] member) {
    return super.zrevrank(key, member);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<byte[]> zrevrange(byte[] key, long start, long stop) {
    return super.zrevrange(key, start, stop);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<Tuple> zrangeWithScores(byte[] key, long start, long stop) {
    return super.zrangeWithScores(key, start, stop);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<Tuple> zrevrangeWithScores(byte[] key, long start, long stop) {
    return super.zrevrangeWithScores(key, start, stop);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zcard(byte[] key) {
    return super.zcard(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Double zscore(byte[] key, byte[] member) {
    return super.zscore(key, member);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Tuple zpopmax(byte[] key) {
    return super.zpopmax(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<Tuple> zpopmax(byte[] key, int count) {
    return super.zpopmax(key, count);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Tuple zpopmin(byte[] key) {
    return super.zpopmin(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<Tuple> zpopmin(byte[] key, int count) {
    return super.zpopmin(key, count);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Transaction multi() {
    return super.multi();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  protected void checkIsInMultiOrPipeline() {
    super.checkIsInMultiOrPipeline();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public void connect() {
    super.connect();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public void disconnect() {
    super.disconnect();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public void resetState() {
    super.resetState();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String watch(byte[]... keys) {
    return super.watch(keys);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String unwatch() {
    return super.unwatch();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> sort(byte[] key) {
    return super.sort(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
    return super.sort(key, sortingParameters);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> blpop(int timeout, byte[]... keys) {
    return super.blpop(timeout, keys);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long sort(byte[] key, SortingParams sortingParameters, byte[] dstkey) {
    return super.sort(key, sortingParameters, dstkey);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long sort(byte[] key, byte[] dstkey) {
    return super.sort(key, dstkey);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> brpop(int timeout, byte[]... keys) {
    return super.brpop(timeout, keys);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> blpop(byte[]... args) {
    return super.blpop(args);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> brpop(byte[]... args) {
    return super.brpop(args);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String auth(String password) {
    return super.auth(password);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String auth(String user, String password) {
    return super.auth(user, password);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Pipeline pipelined() {
    return super.pipelined();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zcount(byte[] key, double min, double max) {
    return super.zcount(key, min, max);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zcount(byte[] key, byte[] min, byte[] max) {
    return super.zcount(key, min, max);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
    return super.zrangeByScore(key, min, max);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {
    return super.zrangeByScore(key, min, max);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
    return super.zrangeByScore(key, min, max, offset, count);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
    return super.zrangeByScore(key, min, max, offset, count);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
    return super.zrangeByScoreWithScores(key, min, max);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
    return super.zrangeByScoreWithScores(key, min, max);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset,
      int count) {
    return super.zrangeByScoreWithScores(key, min, max, offset, count);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset,
      int count) {
    return super.zrangeByScoreWithScores(key, min, max, offset, count);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  protected Set<Tuple> getTupledSet() {
    return super.getTupledSet();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
    return super.zrevrangeByScore(key, max, min);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
    return super.zrevrangeByScore(key, max, min);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
    return super.zrevrangeByScore(key, max, min, offset, count);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count) {
    return super.zrevrangeByScore(key, max, min, offset, count);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
    return super.zrevrangeByScoreWithScores(key, max, min);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset,
      int count) {
    return super.zrevrangeByScoreWithScores(key, max, min, offset, count);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min) {
    return super.zrevrangeByScoreWithScores(key, max, min);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset,
      int count) {
    return super.zrevrangeByScoreWithScores(key, max, min, offset, count);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zremrangeByRank(byte[] key, long start, long stop) {
    return super.zremrangeByRank(key, start, stop);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zremrangeByScore(byte[] key, double min, double max) {
    return super.zremrangeByScore(key, min, max);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zremrangeByScore(byte[] key, byte[] min, byte[] max) {
    return super.zremrangeByScore(key, min, max);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zunionstore(byte[] dstkey, byte[]... sets) {
    return super.zunionstore(dstkey, sets);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zunionstore(byte[] dstkey, ZParams params, byte[]... sets) {
    return super.zunionstore(dstkey, params, sets);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zinterstore(byte[] dstkey, byte[]... sets) {
    return super.zinterstore(dstkey, sets);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zinterstore(byte[] dstkey, ZParams params, byte[]... sets) {
    return super.zinterstore(dstkey, params, sets);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zlexcount(byte[] key, byte[] min, byte[] max) {
    return super.zlexcount(key, min, max);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max) {
    return super.zrangeByLex(key, min, max);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count) {
    return super.zrangeByLex(key, min, max, offset, count);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min) {
    return super.zrevrangeByLex(key, max, min);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count) {
    return super.zrevrangeByLex(key, max, min, offset, count);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long zremrangeByLex(byte[] key, byte[] min, byte[] max) {
    return super.zremrangeByLex(key, min, max);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String save() {
    return super.save();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String bgsave() {
    return super.bgsave();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String bgrewriteaof() {
    return super.bgrewriteaof();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long lastsave() {
    return super.lastsave();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String shutdown() {
    return super.shutdown();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String info() {
    return super.info();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String info(String section) {
    return super.info(section);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public void monitor(JedisMonitor jedisMonitor) {
    super.monitor(jedisMonitor);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String slaveof(String host, int port) {
    return super.slaveof(host, port);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String slaveofNoOne() {
    return super.slaveofNoOne();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> configGet(byte[] pattern) {
    return super.configGet(pattern);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String configResetStat() {
    return super.configResetStat();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String configRewrite() {
    return super.configRewrite();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public byte[] configSet(byte[] parameter, byte[] value) {
    return super.configSet(parameter, value);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public boolean isConnected() {
    return super.isConnected();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long strlen(byte[] key) {
    return super.strlen(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public void sync() {
    super.sync();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long lpushx(byte[] key, byte[]... string) {
    return super.lpushx(key, string);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long persist(byte[] key) {
    return super.persist(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long rpushx(byte[] key, byte[]... string) {
    return super.rpushx(key, string);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public byte[] echo(byte[] string) {
    return super.echo(string);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long linsert(byte[] key, ListPosition where, byte[] pivot, byte[] value) {
    return super.linsert(key, where, pivot, value);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String debug(DebugParams params) {
    return super.debug(params);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Client getClient() {
    return super.getClient();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public byte[] brpoplpush(byte[] source, byte[] destination, int timeout) {
    return super.brpoplpush(source, destination, timeout);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Boolean setbit(byte[] key, long offset, boolean value) {
    return super.setbit(key, offset, value);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Boolean setbit(byte[] key, long offset, byte[] value) {
    return super.setbit(key, offset, value);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Boolean getbit(byte[] key, long offset) {
    return super.getbit(key, offset);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long bitpos(byte[] key, boolean value) {
    return super.bitpos(key, value);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long bitpos(byte[] key, boolean value, BitPosParams params) {
    return super.bitpos(key, value, params);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long setrange(byte[] key, long offset, byte[] value) {
    return super.setrange(key, offset, value);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public byte[] getrange(byte[] key, long startOffset, long endOffset) {
    return super.getrange(key, startOffset, endOffset);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long publish(byte[] channel, byte[] message) {
    return super.publish(channel, message);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public void subscribe(BinaryJedisPubSub jedisPubSub, byte[]... channels) {
    super.subscribe(jedisPubSub, channels);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public void psubscribe(BinaryJedisPubSub jedisPubSub, byte[]... patterns) {
    super.psubscribe(jedisPubSub, patterns);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public int getDB() {
    return super.getDB();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Object eval(byte[] script, List<byte[]> keys, List<byte[]> args) {
    return super.eval(script, keys, args);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Object eval(byte[] script, byte[] keyCount, byte[]... params) {
    return super.eval(script, keyCount, params);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Object eval(byte[] script, int keyCount, byte[]... params) {
    return super.eval(script, keyCount, params);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Object eval(byte[] script) {
    return super.eval(script);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Object evalsha(byte[] sha1) {
    return super.evalsha(sha1);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Object evalsha(byte[] sha1, List<byte[]> keys, List<byte[]> args) {
    return super.evalsha(sha1, keys, args);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Object evalsha(byte[] sha1, int keyCount, byte[]... params) {
    return super.evalsha(sha1, keyCount, params);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String scriptFlush() {
    return super.scriptFlush();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long scriptExists(byte[] sha1) {
    return super.scriptExists(sha1);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<Long> scriptExists(byte[]... sha1) {
    return super.scriptExists(sha1);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public byte[] scriptLoad(byte[] script) {
    return super.scriptLoad(script);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String scriptKill() {
    return super.scriptKill();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String slowlogReset() {
    return super.slowlogReset();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long slowlogLen() {
    return super.slowlogLen();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> slowlogGetBinary() {
    return super.slowlogGetBinary();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> slowlogGetBinary(long entries) {
    return super.slowlogGetBinary(entries);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long objectRefcount(byte[] key) {
    return super.objectRefcount(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public byte[] objectEncoding(byte[] key) {
    return super.objectEncoding(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long objectIdletime(byte[] key) {
    return super.objectIdletime(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> objectHelpBinary() {
    return super.objectHelpBinary();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long objectFreq(byte[] key) {
    return super.objectFreq(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long bitcount(byte[] key) {
    return super.bitcount(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long bitcount(byte[] key, long start, long end) {
    return super.bitcount(key, start, end);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long bitop(BitOP op, byte[] destKey, byte[]... srcKeys) {
    return super.bitop(op, destKey, srcKeys);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public byte[] dump(byte[] key) {
    return super.dump(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String restore(byte[] key, int ttl, byte[] serializedValue) {
    return super.restore(key, ttl, serializedValue);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String restoreReplace(byte[] key, int ttl, byte[] serializedValue) {
    return super.restoreReplace(key, ttl, serializedValue);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long pexpire(byte[] key, long milliseconds) {
    return super.pexpire(key, milliseconds);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long pexpireAt(byte[] key, long millisecondsTimestamp) {
    return super.pexpireAt(key, millisecondsTimestamp);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long pttl(byte[] key) {
    return super.pttl(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String psetex(byte[] key, long milliseconds, byte[] value) {
    return super.psetex(key, milliseconds, value);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public byte[] memoryDoctorBinary() {
    return super.memoryDoctorBinary();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public byte[] aclWhoAmIBinary() {
    return super.aclWhoAmIBinary();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public byte[] aclGenPassBinary() {
    return super.aclGenPassBinary();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> aclListBinary() {
    return super.aclListBinary();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> aclUsersBinary() {
    return super.aclUsersBinary();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public AccessControlUser aclGetUser(byte[] name) {
    return super.aclGetUser(name);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String aclSetUser(byte[] name) {
    return super.aclSetUser(name);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String aclSetUser(byte[] name, byte[]... keys) {
    return super.aclSetUser(name, keys);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long aclDelUser(byte[] name) {
    return super.aclDelUser(name);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> aclCatBinary() {
    return super.aclCatBinary();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> aclCat(byte[] category) {
    return super.aclCat(category);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String clientKill(byte[] ipPort) {
    return super.clientKill(ipPort);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String clientKill(String ip, int port) {
    return super.clientKill(ip, port);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long clientKill(ClientKillParams params) {
    return super.clientKill(params);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public byte[] clientGetnameBinary() {
    return super.clientGetnameBinary();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public byte[] clientListBinary() {
    return super.clientListBinary();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String clientSetname(byte[] name) {
    return super.clientSetname(name);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String clientPause(long timeout) {
    return super.clientPause(timeout);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<String> time() {
    return super.time();
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String migrate(String host, int port, byte[] key, int destinationDb, int timeout) {
    return super.migrate(host, port, key, destinationDb, timeout);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String migrate(String host, int port, int destinationDB, int timeout, MigrateParams params,
      byte[]... keys) {
    return super.migrate(host, port, destinationDB, timeout, params, keys);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long waitReplicas(int replicas, long timeout) {
    return super.waitReplicas(replicas, timeout);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long pfadd(byte[] key, byte[]... elements) {
    return super.pfadd(key, elements);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public long pfcount(byte[] key) {
    return super.pfcount(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String pfmerge(byte[] destkey, byte[]... sourcekeys) {
    return super.pfmerge(destkey, sourcekeys);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long pfcount(byte[]... keys) {
    return super.pfcount(keys);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public ScanResult<byte[]> scan(byte[] cursor) {
    return super.scan(cursor);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public ScanResult<byte[]> scan(byte[] cursor, ScanParams params) {
    return super.scan(cursor, params);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public ScanResult<Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor) {
    return super.hscan(key, cursor);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public ScanResult<Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor, ScanParams params) {
    return super.hscan(key, cursor, params);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public ScanResult<byte[]> sscan(byte[] key, byte[] cursor) {
    return super.sscan(key, cursor);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public ScanResult<byte[]> sscan(byte[] key, byte[] cursor, ScanParams params) {
    return super.sscan(key, cursor, params);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public ScanResult<Tuple> zscan(byte[] key, byte[] cursor) {
    return super.zscan(key, cursor);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public ScanResult<Tuple> zscan(byte[] key, byte[] cursor, ScanParams params) {
    return super.zscan(key, cursor, params);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long geoadd(byte[] key, double longitude, double latitude, byte[] member) {
    return super.geoadd(key, longitude, latitude, member);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long geoadd(byte[] key, Map<byte[], GeoCoordinate> memberCoordinateMap) {
    return super.geoadd(key, memberCoordinateMap);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Double geodist(byte[] key, byte[] member1, byte[] member2) {
    return super.geodist(key, member1, member2);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Double geodist(byte[] key, byte[] member1, byte[] member2, GeoUnit unit) {
    return super.geodist(key, member1, member2, unit);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> geohash(byte[] key, byte[]... members) {
    return super.geohash(key, members);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<GeoCoordinate> geopos(byte[] key, byte[]... members) {
    return super.geopos(key, members);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude,
      double radius, GeoUnit unit) {
    return super.georadius(key, longitude, latitude, radius, unit);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<GeoRadiusResponse> georadiusReadonly(byte[] key, double longitude, double latitude,
      double radius, GeoUnit unit) {
    return super.georadiusReadonly(key, longitude, latitude, radius, unit);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude,
      double radius, GeoUnit unit, GeoRadiusParam param) {
    return super.georadius(key, longitude, latitude, radius, unit, param);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<GeoRadiusResponse> georadiusReadonly(byte[] key, double longitude, double latitude,
      double radius, GeoUnit unit, GeoRadiusParam param) {
    return super.georadiusReadonly(key, longitude, latitude, radius, unit, param);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius,
      GeoUnit unit) {
    return super.georadiusByMember(key, member, radius, unit);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<GeoRadiusResponse> georadiusByMemberReadonly(byte[] key, byte[] member, double radius,
      GeoUnit unit) {
    return super.georadiusByMemberReadonly(key, member, radius, unit);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius,
      GeoUnit unit, GeoRadiusParam param) {
    return super.georadiusByMember(key, member, radius, unit, param);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<GeoRadiusResponse> georadiusByMemberReadonly(byte[] key, byte[] member, double radius,
      GeoUnit unit, GeoRadiusParam param) {
    return super.georadiusByMemberReadonly(key, member, radius, unit, param);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<Long> bitfield(byte[] key, byte[]... arguments) {
    return super.bitfield(key, arguments);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<Long> bitfieldReadonly(byte[] key, byte[]... arguments) {
    return super.bitfieldReadonly(key, arguments);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long hstrlen(byte[] key, byte[] field) {
    return super.hstrlen(key, field);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> xread(int count, long block, Map<byte[], byte[]> streams) {
    return super.xread(count, block, streams);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> xreadGroup(byte[] groupname, byte[] consumer, int count, long block,
      boolean noAck, Map<byte[], byte[]> streams) {
    return super.xreadGroup(groupname, consumer, count, block, noAck, streams);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public byte[] xadd(byte[] key, byte[] id, Map<byte[], byte[]> hash, long maxLen,
      boolean approximateLength) {
    return super.xadd(key, id, hash, maxLen, approximateLength);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long xlen(byte[] key) {
    return super.xlen(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> xrange(byte[] key, byte[] start, byte[] end, long count) {
    return super.xrange(key, start, end, count);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> xrevrange(byte[] key, byte[] end, byte[] start, int count) {
    return super.xrevrange(key, end, start, count);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long xack(byte[] key, byte[] group, byte[]... ids) {
    return super.xack(key, group, ids);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String xgroupCreate(byte[] key, byte[] consumer, byte[] id, boolean makeStream) {
    return super.xgroupCreate(key, consumer, id, makeStream);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public String xgroupSetID(byte[] key, byte[] consumer, byte[] id) {
    return super.xgroupSetID(key, consumer, id);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long xgroupDestroy(byte[] key, byte[] consumer) {
    return super.xgroupDestroy(key, consumer);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long xgroupDelConsumer(byte[] key, byte[] consumer, byte[] consumerName) {
    return super.xgroupDelConsumer(key, consumer, consumerName);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long xdel(byte[] key, byte[]... ids) {
    return super.xdel(key, ids);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Long xtrim(byte[] key, long maxLen, boolean approximateLength) {
    return super.xtrim(key, maxLen, approximateLength);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> xpending(byte[] key, byte[] groupname, byte[] start, byte[] end, int count,
      byte[] consumername) {
    return super.xpending(key, groupname, start, end, count, consumername);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<byte[]> xclaim(byte[] key, byte[] groupname, byte[] consumername, long minIdleTime,
      long newIdleTime, int retries, boolean force, byte[][] ids) {
    return super
        .xclaim(key, groupname, consumername, minIdleTime, newIdleTime, retries, force, ids);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Object sendCommand(ProtocolCommand cmd, byte[]... args) {
    return super.sendCommand(cmd, args);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public StreamInfo xinfoStream(byte[] key) {
    return super.xinfoStream(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<StreamGroupInfo> xinfoGroup(byte[] key) {
    return super.xinfoGroup(key);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public List<StreamConsumersInfo> xinfoConsumers(byte[] key, byte[] group) {
    return super.xinfoConsumers(key, group);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}

  @Override
  public Object sendCommand(ProtocolCommand cmd) {
    return super.sendCommand(cmd);
    Jedis jedis = null;
    try {
      jedis = open();
      return super.type(key);
    } finally {
      close(jedis);
    }
}
}
