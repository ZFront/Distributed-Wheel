package com.wheel.service.mq.monitor.event;

import com.wheel.service.mq.monitor.entity.QueueInfo;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @desc 消息积压事件
 * @author: zhouf
 */
public class BacklogEvent extends ApplicationEvent {

    public BacklogEvent(Object source) {
        super(source);
    }

    public List<QueueInfo> getQueues() {
        return (List<QueueInfo>) super.source;
    }
}
