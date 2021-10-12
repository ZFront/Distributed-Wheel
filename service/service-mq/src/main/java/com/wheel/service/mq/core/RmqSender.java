package com.wheel.service.mq.core;

import com.wheel.common.enums.mq.NotifyTypeEnum;
import com.wheel.common.enums.mq.RmqDelayLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @description rockMQ 业务发送类
 * 采用  RocketMQTemplate 进行实现。 ==> spring message 风格
 * 封装相对复杂
 *
 * <p>
 * rockMq 最佳实践
 * {@url https://github.com/apache/rocketmq/blob/master/docs/cn/best_practice.md}
 * @author: zhouf
 * @date: 2020/5/29
 */
@Slf4j
@Component
public class RmqSender {

    /**
     * 调用过程中，优先使用 {@link org.springframework.messaging.Message}, 方便设计消息头及对应的实体
     */
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public RmqSender(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     * 单边发送
     *
     * @param notifyType
     * @param trxNo
     * @param msg
     */
    public void sendOneWay(NotifyTypeEnum notifyType, String trxNo, String msg) {
        Message message = buildMessage(trxNo, msg, null);
        rocketMQTemplate.sendOneWay(getRmqDestination(notifyType), message);
    }

    /**
     * 发送单边延时调用
     *
     * @param notifyType
     * @param trxNo
     * @param msg
     * @param delayLevel
     */
    public void sendOneWayDelay(NotifyTypeEnum notifyType, String trxNo, String msg, RmqDelayLevel delayLevel) {
        Message message = buildMessage(trxNo, msg, delayLevel);
        rocketMQTemplate.sendOneWay(getRmqDestination(notifyType), message);
    }

    /**
     * 同步发送
     *
     * @param notifyType
     * @param trxNo
     * @param msg
     */
    public boolean sendSync(NotifyTypeEnum notifyType, String trxNo, String msg) {
        Message message = buildMessage(trxNo, msg, null);
        SendResult sendResult = rocketMQTemplate.syncSend(getRmqDestination(notifyType), message);
        return SendStatus.SEND_OK.equals(sendResult.getSendStatus());
    }

    /**
     * 同步延时发送
     *
     * @param notifyType
     * @param trxNo
     * @param msg
     * @param delayLevel
     * @return
     */
    public boolean sendSyncDelay(NotifyTypeEnum notifyType, String trxNo, String msg, RmqDelayLevel delayLevel) {
        Message message = buildMessage(trxNo, msg, delayLevel);
        SendResult sendResult = rocketMQTemplate.syncSend(getRmqDestination(notifyType), message);
        return SendStatus.SEND_OK.equals(sendResult.getSendStatus());
    }

    /**
     * 异步发送
     *
     * @param notifyType
     * @param trxNo
     * @param msg
     */
    public void sendAsync(NotifyTypeEnum notifyType, String trxNo, String msg, SendCallback sendCallback) {
        Message message = buildMessage(trxNo, msg, null);
        rocketMQTemplate.asyncSend(getRmqDestination(notifyType), message, sendCallback);
    }

    /**
     * 异步发送
     *
     * @param notifyType
     * @param trxNo
     * @param msg
     */
    public void sendAsyncDelay(NotifyTypeEnum notifyType, String trxNo, String msg, SendCallback sendCallback, RmqDelayLevel delayLevel) {
        Message message = buildMessage(trxNo, msg, delayLevel);
        rocketMQTemplate.asyncSend(getRmqDestination(notifyType), message, sendCallback);
    }

    /**
     * {@link RocketMQUtil getAndWrapMessage} 此方法中会对 destination 进行:分割
     * 前段为 topic
     * 后段为 tags
     *
     * @param notifyType
     * @return
     */
    private String getRmqDestination(NotifyTypeEnum notifyType) {
        return notifyType.getDestination() + ":" + notifyType.getValue();
    }

    /**
     * {@link RocketMQUtil getAndWrapMessage} 此方法中会对header中的KEYS进行保存
     *
     * @param trxNo
     * @param msg
     * @return
     */
    private Message buildMessage(String trxNo, String msg, RmqDelayLevel delayLevel) {
        MessageBuilder builder = MessageBuilder.withPayload(msg);
        // 设置KEYS， 用于存储keys
        builder.setHeader(MessageConst.PROPERTY_KEYS, trxNo);
        if (delayLevel != null) {
            // 设置延迟信息
            builder.setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, delayLevel.getValue());
        }
        return builder.build();
    }
}
