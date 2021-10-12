package com.wheel.demo.test.mq.biz;

import com.wheel.common.enums.mq.NotifyTypeEnum;
import com.wheel.common.util.JsonUtil;
import com.wheel.demo.test.mq.vo.RetryNotifyVo;
import com.wheel.service.mq.core.AmqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.TreeMap;

/**
 * @desc
 * @author: zhouf
 */
@Component
public class NotityDemoBiz {

    @Autowired
    private AmqSender amqSender;

    /**
     * DEMO：
     * 发送重试通知
     *
     * @param trxNo
     */
    public void sendRetryNotify(String trxNo) {
        // 配置重试策略，分别为5、10、15
        TreeMap<Integer, Integer> retryRules = new TreeMap<>();
        retryRules.put(1, 5 * 60);
        retryRules.put(2, 10 * 60);
        retryRules.put(3, 15 * 60);

        RetryNotifyVo vo = new RetryNotifyVo(retryRules);
        vo.setTrxNo(trxNo);

        sendDelayNotify(trxNo, JsonUtil.toString(vo), vo.currentRetryTime());
    }

    /**
     * 发送延迟通知
     *
     * @param trxNo
     * @param msg
     * @param delayTimeSec
     */
    public void sendDelayNotify(String trxNo, String msg, int delayTimeSec) {
        amqSender.sendDelayQueue(NotifyTypeEnum.QUEUE_TEST_NOTIFY, trxNo, msg, delayTimeSec);
    }

}
