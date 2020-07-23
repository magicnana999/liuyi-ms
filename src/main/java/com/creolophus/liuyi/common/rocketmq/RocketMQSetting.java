package com.creolophus.liuyi.common.rocketmq;

/**
 * @author magicnana
 * @date 2019/6/10 上午10:04
 */
public class RocketMQSetting {

    private String namesrvAddr = "127.0.0.1:10911";
    private String producerGroupName = "liuyi";

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getProducerGroupName() {
        return producerGroupName;
    }

    public void setProducerGroupName(String producerGroupName) {
        this.producerGroupName = producerGroupName;
    }
}
