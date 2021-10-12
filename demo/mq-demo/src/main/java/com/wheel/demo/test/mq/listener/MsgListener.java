package com.wheel.demo.test.mq.listener;

import com.wheel.common.constant.mq.P2PDest;
import com.wheel.common.constant.mq.VTopicConsume;
import com.wheel.common.util.JsonUtil;
import com.wheel.demo.test.mq.biz.NotityDemoBiz;
import com.wheel.demo.test.mq.vo.RetryNotifyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @desc
 * @author: zhouf
 */
@Slf4j
@Component
public class MsgListener {

    @Autowired
    private NotityDemoBiz notityDemoBiz;

    /**
     * 接收queue消息
     *
     * @param msg
     */
    @JmsListener(destination = P2PDest.QUEUE_DELAY_TEST)
    public void receiveMsg(String msg) {
        log.info("queue消息接收：msg={}", msg);

        RetryNotifyVo vo = JsonUtil.toBean(msg, RetryNotifyVo.class);

        int nextDelayTime = vo.nextDelayTime();
        String trxNo = vo.getTrxNo();

        if (-1 != nextDelayTime) {
            log.info("trxNo={}, 将在{}秒后再次发送补单通知", trxNo, nextDelayTime);
            notityDemoBiz.sendDelayNotify(trxNo, JsonUtil.toString(vo), nextDelayTime);
        } else {
            log.info("trxNo={}, 当前补单次数为:{},补单次数已用尽，不再发送补单通知", trxNo, vo.getCurrentRetryTimes());
        }
    }

    /**
     *
     * @param msg
     */
    @JmsListener(destination = VTopicConsume.TOPIC_TEST_CONSUMER_A)
    public void receiveTopicA(String msg) {
        log.info("topic consumerA 接收消息：msg={}", msg);
    }

    /**
     *
     * @param msg
     */
    @JmsListener(destination = VTopicConsume.TOPIC_TEST_CONSUMER_B)
    public void receiveTopicB(String msg) {
        log.info("topic consumerB 接收消息：msg={}", msg);
    }
}
