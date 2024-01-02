package com.creolophus.lucky.common.id;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author magicnana
 * @date 2019/10/18 下午2:20
 */
public class ObjectID {

  private static final AtomicInteger atomicInteger = new AtomicInteger(0);
  private static final int machineId = MachineInfo.getMachineIdentifier();
  private static final short processId = MachineInfo.getProcessIdentifier();

  public static void main(String[] args) throws InterruptedException {
    //        ObjectID objectID = new ObjectID();
    //        String id1 = objectID.nextId();
    //        TimeUnit.SECONDS.sleep(1);
    //        String id2 = objectID.nextId();
    //        String id3 = objectID.nextId();
    //        System.out.println(id1.compareTo(id2));
    //        System.out.println(id2.compareTo(id3));

    System.out.println("be7fcee88904a31d40064240ac13d931".hashCode());
  }

  public String nextId() {
    Date now = new Date();
    int counter = atomicInteger.incrementAndGet();
    return new org.bson.types.ObjectId(now, machineId, processId, counter).toHexString();
  }

  @Override
  public String toString() {
    return nextId();
  }
}
