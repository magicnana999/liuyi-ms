package com.creolophus.liuyi.common.rocketmq;

import com.creolophus.liuyi.common.exception.DoNotReConsumeException;
import com.creolophus.liuyi.common.logger.Entry;
import com.creolophus.liuyi.common.shutdown.Shutdown;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author magicnana
 * @date 2019/7/23 下午5:54
 */
public abstract class RocketMQConsumer implements Shutdown {

    private static final Logger logger = LoggerFactory.getLogger(RocketMQProducer.class);


    @Resource
    private RocketMQSetting rocketMQSetting;

    private final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();

    protected abstract String getConsumerGroup();
    protected abstract String getConsumerTopic();

    @Entry
    protected abstract void process(String msgId, String topic, String msgBody, int times);


    @PostConstruct
    public void init() {
        Thread thread = new Thread(new ConsumerTask(), "rocketmq_consumer_"+getConsumerTopic());
        thread.start();
        logger.info("start RocketMQ Consumer "+thread.getName());

    }

    private class ConsumerTask implements Runnable {

        @Override
        public void run() {
            consumer.setNamesrvAddr(rocketMQSetting.getNamesrvAddr());
            consumer.setConsumerGroup(getConsumerGroup());
            try {
                //订阅
                consumer.subscribe(getConsumerTopic(),"*");
                consumer.registerMessageListener(new MessageListenerConcurrently() {

                    @Override
                    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                        for (MessageExt msg : msgs) {
                            String msgBody = new String(msg.getBody());
                            try{
                                process(msg.getMsgId(),msg.getTopic(),msgBody,msg.getReconsumeTimes());
                            } catch (DoNotReConsumeException e) {
                                logger.error("RocketMQ Consumer Error,Do Not Reconsume",e);
                                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                            }catch (Throwable e){
                                logger.error("RocketMQ Consumer Error,Reconsume Later",e);
                                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                            }
                        }
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                });
                consumer.start();
            }catch (Throwable e){
                throw new RuntimeException(e);
            }
        }
    }

    @Override
	public void shutdown() {
		consumer.shutdown();
	}
}