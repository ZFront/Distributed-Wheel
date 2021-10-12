package com.wheel.demo.test.mq;

import com.wheel.common.enums.mq.NotifyTypeEnum;
import com.wheel.service.mq.core.AmqSender;
import com.wheel.demo.test.mq.biz.NotityDemoBiz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @desc
 * @author: zhouf
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqDemoApplication.class)
public class TsSendMq {

    @Autowired
    private NotityDemoBiz notityDemoBiz;

    @Autowired
    private AmqSender amqSender;

    @Test
    public void testRetryMq() {
        notityDemoBiz.sendRetryNotify("ceshi");
    }

    @Test
    public void testSendQueue() {
        amqSender.sendQueue(NotifyTypeEnum.QUEUE_TEST_NOTIFY, "123", "4321");
    }

    @Test
    public void testSendTopic() {
        amqSender.sendTopic(NotifyTypeEnum.TOPIC_TEST_NOTIFY, "234", "这是一条topic测试");
    }
}
