package com.creolophus.liuyi.common.id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 朝辞白帝彩云间 千行代码一日还
 * 两岸领导啼不住 地铁已到回龙观
 *
 * @author magicnana
 * @date 2019/7/3 下午2:44
 */
public final class SimpleSnowflakeID {

    protected static final Logger logger = LoggerFactory.getLogger(SimpleSnowflakeID.class);

    private long workerId = 0L;
    private long datacenterId = 0L;

    private long sequence = 0L;

    private long twepoch = 1490235000567L;

    /**
     * 原来是 5
     */
    private long workerIdBits = 8L;
    /**
     * 原来是 5
     */
    private long datacenterIdBits = 0L;
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    /**
     * 原来是 12
     */
    private long sequenceBits = 14L;

    private long workerIdShift = sequenceBits;
    private long datacenterIdShift = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;

    public SimpleSnowflakeID() {
    }

    public SimpleSnowflakeID(long workerId, long twepoch) {
        this.setWorkerId(workerId);
        this.setTwepoch(twepoch);
    }


    public SimpleSnowflakeID(long twepoch) {
        String str = MachineInfo.getMachineIdentifier() + MachineInfo.getProcessIdentifier() + "";
        this.setWorkerId(str.hashCode() & maxWorkerId);
        this.setTwepoch(twepoch);
    }

    public void setTwepoch(long twepoch) {
        this.twepoch = twepoch;
        logger.info("set twepoch = {}", twepoch);
    }

    public void setWorkerId(long workerId) {
        if(workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.workerId = workerId;
        logger.info("set workerId = {}", workerId);
    }

    public long getWorkerId() {
        return workerId;
    }


    public synchronized long nextId() {
        long timestamp = timeGen();

        if(timestamp < lastTimestamp) {
            logger.error("clock is moving backwards.  Rejecting requests until {}.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if(lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if(sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while(timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) throws InterruptedException {


        SimpleSnowflakeID id = new SimpleSnowflakeID();
        id.setWorkerId(1);
        id.setTwepoch(845996401676L);
        String next = id.nextId() + "";
        System.out.println(next.length() + " " + next);

        SimpleSnowflakeID id2 = new SimpleSnowflakeID();
        id2.setWorkerId(1);
        id2.setTwepoch(1318936401676L);
        String next2 = id2.nextId() + "";
        System.out.println(next2.length() + " " + next2);


        SimpleSnowflakeID id3 = new SimpleSnowflakeID();
        id3.setWorkerId(1);
        id3.setTwepoch(617996401676L);
        String next3 = id3.nextId() + "";
        System.out.println(next3.length() + " " + next3);

    }


}
