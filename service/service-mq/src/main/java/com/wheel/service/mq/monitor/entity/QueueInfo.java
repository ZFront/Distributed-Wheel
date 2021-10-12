package com.wheel.service.mq.monitor.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @desc 队列信息
 * @author: zhouf
 */
@Getter
@Setter
public class QueueInfo {

    /**
     * Broker名称
     */
    private String brokerName;

    /**
     * 队列名
     */
    private String queueName;

    /**
     * 队列剩余的消息数量
     */
    private long queueSize;

    /**
     * 生产者数量
     */
    private long producerCount;

    /**
     * 消费者数量
     */
    private long consumerCount;

    /**
     * 入队消息总数
     */
    private long enqueueCount;

    /**
     * 出队消息总数
     */
    private long dequeueCount;

    /**
     * 消费者是否被暂停
     */
    private boolean paused;

}
