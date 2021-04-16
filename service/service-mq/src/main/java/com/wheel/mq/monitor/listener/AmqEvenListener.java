package com.wheel.mq.monitor.listener;

import com.wheel.common.util.JsonUtil;
import com.wheel.mq.monitor.entity.QueueInfo;
import com.wheel.mq.monitor.event.BacklogEvent;
import com.wheel.mq.monitor.event.ConsumeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @desc Active 事件监听
 * @author: zhouf
 */
@Slf4j
@Component
public class AmqEvenListener {

    /**
     * 消息积压事件监听
     *
     * @param backlogEvent
     */
    @EventListener
    public void backlogEvent(BacklogEvent backlogEvent) {

        // 消息积压的队列
        List<QueueInfo> backlogQueues = backlogEvent.getQueues();

        log.info("消息积压事件接收，{}", JsonUtil.toString(backlogQueues));

        // TODO 发送邮件预警
    }

    /**
     * 消费者事件
     *
     * @param consumeEvent
     */
    @EventListener
    public void consumeEvent(ConsumeEvent consumeEvent) {

        List<QueueInfo> queues = consumeEvent.getQueues();

        log.info("消费者事件接收,{}", JsonUtil.toString(queues));

        // TODO 发送邮件预警
    }

}
