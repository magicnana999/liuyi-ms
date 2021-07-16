package com.creolophus.liuyi.common.datasource;

public class DataSourceSetting {

  private String username;
  private String password;
  private String url;
  private String driver;

  private int maxActive;
  private int initialSize;
  private long maxWait;
  private int minIdle;
  private long timeBetweenEvictionRunsMillis;
  private long minEvictableIdleTimeMillis;
  private String validationQuery;
  private boolean testWhileIdle;
  private boolean testOnBorrow;
  private boolean testOnReturn;
  private boolean removeAbandoned;
  private int removeAbandonedTimeout;
  private boolean logAbandoned;

  public String getDriver() {
    return driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public int getInitialSize() {
    return initialSize;
  }

  public void setInitialSize(int initialSize) {
    this.initialSize = initialSize;
  }

  public int getMaxActive() {
    return maxActive;
  }

  public void setMaxActive(int maxActive) {
    this.maxActive = maxActive;
  }

  public long getMaxWait() {
    return maxWait;
  }

  public void setMaxWait(long maxWaitMillis) {
    this.maxWait = maxWaitMillis;
  }

  public long getMinEvictableIdleTimeMillis() {
    return minEvictableIdleTimeMillis;
  }

  public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
    this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
  }

  public int getMinIdle() {
    return minIdle;
  }

  public void setMinIdle(int minIdle) {
    this.minIdle = minIdle;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getRemoveAbandonedTimeout() {
    return removeAbandonedTimeout;
  }

  public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
    this.removeAbandonedTimeout = removeAbandonedTimeout;
  }

  public long getTimeBetweenEvictionRunsMillis() {
    return timeBetweenEvictionRunsMillis;
  }

  public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
    this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getValidationQuery() {
    return validationQuery;
  }

  public void setValidationQuery(String validationQuery) {
    this.validationQuery = validationQuery;
  }

  public boolean isLogAbandoned() {
    return logAbandoned;
  }

  public void setLogAbandoned(boolean logAbandoned) {
    this.logAbandoned = logAbandoned;
  }

  public boolean isRemoveAbandoned() {
    return removeAbandoned;
  }

  public void setRemoveAbandoned(boolean removeAbandoned) {
    this.removeAbandoned = removeAbandoned;
  }

  public boolean isTestOnBorrow() {
    return testOnBorrow;
  }

  public void setTestOnBorrow(boolean testOnBorrow) {
    this.testOnBorrow = testOnBorrow;
  }

  public boolean isTestOnReturn() {
    return testOnReturn;
  }

  public void setTestOnReturn(boolean testOnReturn) {
    this.testOnReturn = testOnReturn;
  }

  public boolean isTestWhileIdle() {
    return testWhileIdle;
  }

  public void setTestWhileIdle(boolean testWhileIdle) {
    this.testWhileIdle = testWhileIdle;
  }
}
