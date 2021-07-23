package com.creolophus.liuyi.common.rocketmq;

import com.creolophus.liuyi.common.api.MdcUtil;
import com.creolophus.liuyi.common.exception.DoNotReConsumeException;
import com.creolophus.liuyi.common.logger.TracerUtil;
import com.creolophus.liuyi.common.thread.Stopable;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author magicnana
 * @date 2019/7/23 下午5:54
 */
public abstract class RocketMQConsumer implements Stopable {

  private static final Logger logger = LoggerFactory.getLogger(RocketMQProducer.class);
  private final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
  @Resource
  private RocketMQSetting rocketMQSetting;

  @Autowired(required = false)
  private TracerUtil tracerUtil;

  protected abstract String getConsumerGroup();

  protected abstract String getConsumerTopic();

  @PostConstruct
  public void init() {
    //        Thread thread = new Thread(new ConsumerTask(),
    // "rocketmq_consumer_"+getConsumerTopic());
    //        thread.start();

    consumer.setNamesrvAddr(rocketMQSetting.getNamesrvAddr());
    consumer.setConsumerGroup(getConsumerGroup());
    try {
      // 订阅
      consumer.subscribe(getConsumerTopic(), "*");
      consumer.registerMessageListener(
          (MessageListenerConcurrently)
              (msgs, context) -> {
                MdcUtil.setMethod("consumeMessage");
                MdcUtil.setExt("UnknownAppKeyInConsumer");
                for (MessageExt msg : msgs) {
                  MdcUtil.setUri(msg.getMsgId() + "." + msg.getReconsumeTimes());
                  String msgBody = new String(msg.getBody());
                  try {
                    if (tracerUtil != null) {
                      tracerUtil.begin("consume", RocketMQConsumer.class.getSimpleName());
                    }
                    if (logger.isDebugEnabled()) {
                      logger.debug("{}", msgBody);
                    }
                    process(msg.getMsgId(), msg.getTopic(), msgBody, msg.getReconsumeTimes());
                  } catch (DoNotReConsumeException e) {
                    logger.error("error & do not reconsume", e);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                  } catch (Throwable e) {
                    logger.error("error & reconsume later", e);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                  }
                  if (logger.isDebugEnabled()) {
                    logger.debug("success ", msg.getMsgId(), msg.getReconsumeTimes(), msgBody);
                  }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
              });
      consumer.start();
      if (logger.isInfoEnabled()) {
        logger.info("start RocketMQ Consumer " + Thread.currentThread().getName());
      }
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  protected abstract void process(String msgId, String topic, String msgBody, int times);

  //    private class ConsumerTask implements Runnable {
  //
  //        @Override
  //        public void run() {
  //            consumer.setNamesrvAddr(rocketMQSetting.getNamesrvAddr());
  //            consumer.setConsumerGroup(getConsumerGroup());
  //            try {
  //                //订阅
  //                consumer.subscribe(getConsumerTopic(),"*");
  //                consumer.registerMessageListener(new MessageListenerConcurrently() {
  //
  //                    @Override
  //                    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
  // ConsumeConcurrentlyContext context) {
  //                        for (MessageExt msg : msgs) {
  //                            String msgBody = new String(msg.getBody());
  //                            try{
  //
  // process(msg.getMsgId(),msg.getTopic(),msgBody,msg.getReconsumeTimes());
  //                            } catch (DoNotReConsumeException e) {
  //                                logger.error("RocketMQ Consumer Error,Do Not Reconsume",e);
  //                                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
  //                            }catch (Throwable e){
  //                                logger.error("RocketMQ Consumer Error,Reconsume Later",e);
  //                                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
  //                            }
  //                        }
  //                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
  //                    }
  //                });
  //                consumer.start();
  //            }catch (Throwable e){
  //                throw new RuntimeException(e);
  //            }
  //        }
  //    }

  @Override
  public void shutdown() {
    consumer.shutdown();
  }
}
