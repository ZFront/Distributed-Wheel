package com.wheel.mq.demo.listener;

import com.wheel.common.constant.P2PDestinations;
import com.wheel.common.util.JsonUtil;
import com.wheel.mq.demo.biz.NotityDemoBiz;
import com.wheel.mq.demo.vo.RetryNotifyVo;
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

    @JmsListener(destination = P2PDestinations.QUEUE_TEST)
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
}
