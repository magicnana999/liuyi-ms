package org.beetl.sql;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author magicnana
 * @date 2020/7/22 下午4:05
 */
public class Test {

  private static final ConcurrentHashMap<String, String> table = new ConcurrentHashMap<>();

  public void set() {
    String origin = table
        .computeIfAbsent(Thread.currentThread().getName(), new Function<String, String>() {
          @Override
          public String apply(String s) {
            System.out.println("计算中");
            return Thread.currentThread().getName() + " " + System.currentTimeMillis();
          }
        });
    System.out.println(Thread.currentThread().getName() + " " + origin + " " + table
        .get(Thread.currentThread().getName()));
  }

  public void test() throws InterruptedException {
    for (int i = 0; i < 5; i++) {
      Thread t = new Thread(() -> {
        for (int j = 0; j < 10; j++) {
          try {
            TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          Test test = new Test();
          test.set();
        }
      });
      t.start();
      TimeUnit.SECONDS.sleep(1);
    }

    Thread.currentThread().join();
  }
}
