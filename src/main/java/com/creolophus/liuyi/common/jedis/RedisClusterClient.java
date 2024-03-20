package com.creolophus.liuyi.common.jedis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

/**
 * @author magicnana
 * @date 2019/5/14 上午10:24
 */
public class RedisClusterClient extends JedisCluster implements RedisClient {

  public RedisClusterClient(Set<HostAndPort> nodes, GenericObjectPoolConfig poolConfig) {
    super(nodes, poolConfig);
  }

  @Override
  public Set<String> evalSetString(String script, List<String> keys, List<String> args) {
    return (Set<String>) eval(script, keys, args);
  }

  @Override
  public boolean lockIn(String key, long second) {
    final String value = System.currentTimeMillis()+Thread.currentThread().getId()+""+ RandomUtils.nextInt();
    final String nxxx = "NX";
    final String expx = "EX";
    boolean ret;
    if (second <= 0L) {
      throw new RuntimeException("expire time could not be less than 0");
    } else {
      ret = "ok".equalsIgnoreCase(set(key, value, nxxx, expx, second));
    }
    return ret;
  }

  @Override
  public boolean lock(String key) {
    final String value = String.valueOf(System.currentTimeMillis());
    Long ret = setnx(key, value);
    return ret == 1;
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
    if (StringUtils.isBlank(pattern)) {
      return Collections.emptyList();
    }
    List<String> list = new ArrayList<>();
    String cursor = ScanParams.SCAN_POINTER_START;
    ScanParams scanParams = new ScanParams();
    scanParams.count(count);
    scanParams.match(pattern);
    do {
      ScanResult<String> scanResult = scan(cursor, scanParams);
      list.addAll(scanResult.getResult());
      cursor = scanResult.getStringCursor();
    } while (!"0".equals(cursor));
    return list;
  }

}
