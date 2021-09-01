package com.wheel.mq.listener;

import com.wheel.common.constant.mq.P2PDestinations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @description
 * @author: zhouf
 * @date: 2020/5/28
 */
@Slf4j
@Component
public class AmqMsgListener {

    @JmsListener(destination = P2PDestinations.QUEUE_TEST)
    public void resvQueueMsg(String msg) {
        log.info("queue消息接收：msg={}", msg);
    }
}
