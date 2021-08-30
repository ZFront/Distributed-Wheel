package com.wheel.common.enums.mq;

import com.wheel.common.constant.P2PDestinations;
import com.wheel.common.constant.TopicDestionations;

import java.util.Arrays;

/**
 * @description
 * @author: zhouf
 * @date: 2020/5/28
 */
public enum NotifyTypeEnum {

    /**
     * queue通知
     */
    QUEUE_TEST_NOTIFY("queue测试", 1, P2PDestinations.QUEUE_TEST),

    /**
     * topic通知
     */
    TOPIC_TEST_NOTIFY("topic测试", 2, TopicDestionations.TOPIC_TEST),
    ;

    /**
     * 枚举值
     */
    private int value;
    /**
     * 描述
     */
    private String desc;
    /**
     * 队列名
     */
    private String destination;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    NotifyTypeEnum(String desc, int value, String destination) {
        this.value = value;
        this.desc = desc;
        this.destination = destination;
    }


    public static NotifyTypeEnum getEnum(int value) {
        return Arrays.stream(values()).filter(p -> p.value == value).findFirst().orElse(null);
    }
}
