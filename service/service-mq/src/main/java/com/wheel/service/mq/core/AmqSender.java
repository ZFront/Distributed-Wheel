package com.wheel.service.mq.core;

import com.wheel.common.enums.mq.DestTypeEnum;
import com.wheel.common.enums.mq.NotifyTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsTemplate;

/**
 * @description ActiveMq 业务通知类
 * @author: zhouf
 * @date: 2020/5/28
 */
@Slf4j
public class AmqSender {

    private JmsTemplate jmsTemplate;

    public AmqSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * 发送一个queue消息
     *
     * @param notifyType 消息类型
     * @param trxNo      流水号
     * @param msg        消息体
     */
    public void sendQueue(NotifyTypeEnum notifyType, String trxNo, String msg) {
        this.send(notifyType, trxNo, msg, DestTypeEnum.QUEUE.getValue(), 0);
    }

    /**
     * 发送一个延时queue消息
     *
     * @param notifyType 消息类型
     * @param trxNo      流水号
     * @param msg        消息体
     * @param delayTime  单位秒
     */
    public void sendDelayQueue(NotifyTypeEnum notifyType, String trxNo, String msg, int delayTime) {
        this.send(notifyType, trxNo, msg, DestTypeEnum.QUEUE.getValue(), delayTime);
    }

    /**
     * 发送一个topic消息
     *
     * @param notifyType 消息类型
     * @param trxNo      流水号
     * @param msg        消息体
     */
    public void sendTopic(NotifyTypeEnum notifyType, String trxNo, String msg) {
        this.send(notifyType, trxNo, msg, DestTypeEnum.TOPIC.getValue(), 0);
    }


    /**
     * 发送一个延时topic消息
     *
     * @param notifyType 消息类型
     * @param trxNo      流水号
     * @param msg        消息体
     * @param delayTime  单位秒
     */
    public void sendTopic(NotifyTypeEnum notifyType, String trxNo, String msg, int delayTime) {
        this.send(notifyType, trxNo, msg, DestTypeEnum.TOPIC.getValue(), delayTime);
    }

    private void send(NotifyTypeEnum notifyType, String trxNo, String msg, int destinationType, int delayTime) {
        log.info("trxNo={} sendMq , notifyType={}, msg={}, destinationType={}, delayTime={}", trxNo, notifyType.getDesc(), msg, destinationType, delayTime);
        try {
            if (DestTypeEnum.TOPIC.getValue() == destinationType) {
                jmsTemplate.convertAndSend(new ActiveMQTopic(notifyType.getDestination()), msg,
                        postProcessor -> {
                            postProcessor.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayTime * 1000);
                            return postProcessor;
                        });
            } else {
                jmsTemplate.convertAndSend(new ActiveMQQueue(notifyType.getDestination()), msg,
                        postProcessor -> {
                            postProcessor.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayTime * 1000);
                            return postProcessor;
                        });
            }
        } catch (Exception e) {
            log.error("trxNo={} sendMq ERROR", trxNo, e);
        }
    }
}
