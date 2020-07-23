package com.creolophus.liuyi.common.rocketmq;

import com.creolophus.liuyi.common.exception.ApiException;
import com.creolophus.liuyi.common.exception.BrokenException;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.List;

public class RocketMQProducer {


    private static final Logger logger = LoggerFactory.getLogger(RocketMQProducer.class);


    private DefaultMQProducer producer;

    private String namesrvAddr;
    private String producerGroupName;

    public RocketMQProducer(String namesrvAddr,String producerGroupName){
        this.namesrvAddr = namesrvAddr;
        this.producerGroupName = producerGroupName;
    }

    @PostConstruct
    public void init(){
        try {
            producer = new DefaultMQProducer(producerGroupName);
            producer.setNamesrvAddr(namesrvAddr);
            producer.setDefaultTopicQueueNums(8);
            producer.start();
            logger.info("start RocketMQ Producer");
        } catch (Throwable e) {
            throw new BrokenException("RocketMQProducer could not started",e);
        }
    }

    public String send(String topic,byte[] body){
        return send(topic,body,null);
    }

    public String send(String topic,byte[] body,String keys){
        Message message = new Message();
        message.setBody(body);
        message.setTopic(topic);
        if(StringUtils.isNotBlank(keys)){
            message.setKeys(keys);
        }
        return send(message);
    }

    private String  send(Message msg){
        try {
            SendResult sendResult = null;
            if(StringUtils.isNotBlank(msg.getKeys())) {
                sendResult = producer.send(msg, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        int index = Math.abs(msg.getKeys().hashCode()) % mqs.size();
                        return mqs.get(index);
                    }
                }, 0);
            } else {
                sendResult = producer.send(msg);
            }

            logger.info("send to rocketmq {} {} {} {} {}", msg.getTopic(), msg.getKeys(), sendResult == null ? "" : sendResult.getSendStatus(), sendResult == null ? "" : sendResult.getMsgId(), new String(msg.getBody()));


            if(sendResult == null) {
                throw new RuntimeException("Send message to RocketMQ failed, return null");
            }

            if(!sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
                throw new RuntimeException("Send message to RocketMQ failed, " + sendResult.getSendStatus().name());
            }

            return sendResult.getMsgId();

        }catch(RuntimeException e){
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
