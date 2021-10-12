package com.wheel.service.mq.monitor.event;

import com.wheel.service.mq.monitor.entity.QueueInfo;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @desc 消费者错误事件
 * @author: zhouf
 */
public class ConsumeEvent extends ApplicationEvent {

    public ConsumeEvent(Object source) {
        super(source);
    }

    public List<QueueInfo> getQueues() {
        return (List<QueueInfo>) super.source;
    }
}
