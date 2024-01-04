package com.creolophus.common.thread;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author magicnana
 * @date 2020/7/2 下午5:56
 */
public class StopableThread extends Thread implements Stopable {

  private static final Logger logger = LoggerFactory.getLogger(StopableThread.class);

  private static final int JOIN_TIME = 90 * 1000;
  private volatile boolean stopped = false;
  private Runnable runnable;

  public StopableThread(Runnable r) {
    this.runnable = r;
  }

  public StopableThread(Runnable target, String name) {
    super(name);
    this.runnable = target;
  }

//  public static void main(String[] args) {
//    List<StopableThread> list = new ArrayList();
//
//    for (int j = 0; j < 2; j++) {
//      list.add(
//          new StopableThread(
//              () -> {
//                for (int i = 0; i < 5; i++) {
//                  sleep(1);
//                  System.out.println(Thread.currentThread().getName() + " " + i + " doing");
//                }
//              }));
//    }
//
//    list.forEach(thread -> thread.start());
//
//    sleep(5);
//    System.out.println("现在开始停止");
//    list.forEach(thread -> thread.shutdown());
//    System.out.println("所有线程已停止");
//  }

  public static void sleep(int times) {
    try {
      TimeUnit.SECONDS.sleep(times);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void run() {
    while (true && !stopped) {
      runnable.run();
    }
    if (logger.isDebugEnabled()) {
      logger.debug("Thread [{}] has stopped or finished", Thread.currentThread().getName());
    }
  }

  @Override
  public void shutdown() {
    shutdown(false);
  }

  public void shutdown(final boolean interrupt) {
    this.stopped = true;
    try {
      if (interrupt) {
        super.interrupt();
      }
      super.join(JOIN_TIME); // 1分半还执行不完就不等了.
    } catch (InterruptedException e) {
      logger.error("Thread [{}] Interrupted", Thread.currentThread().getName(), e);
    }
  }
}
