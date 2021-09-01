package com.wheel.mq.listener;

import com.wheel.common.constant.mq.P2PDestinations;
import com.wheel.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @description
 * @author: zhouf
 * @date: 2020/5/28
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = P2PDestinations.QUEUE_TEST, consumerGroup = "testGroup")
public class RmqMsgListener implements RocketMQListener {

    @Override
    public void onMessage(Object o) {
        log.info("rocketMq 接收到消息：{}", JsonUtil.toString(o));
    }
}
