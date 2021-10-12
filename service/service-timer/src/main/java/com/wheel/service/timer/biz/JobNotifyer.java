package com.wheel.service.timer.biz;

import com.wheel.common.enums.mq.NotifyTypeEnum;
import com.wheel.facade.timer.entity.JobTask;
import com.wheel.service.mq.core.AmqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @description 业务分发类
 * @author: zhouf
 * @date: 2020/7/16
 */
@Component
public class JobNotifyer {

    @Autowired
    private AmqSender amqSender;

    public void sendNotify(JobTask jobTask) {
        // 可根据实际使用情况，进行各自业务实现
        amqSender.sendQueue(NotifyTypeEnum.QUEUE_TEST_NOTIFY, UUID.randomUUID().toString(), jobTask.getParamJson());
    }
}
