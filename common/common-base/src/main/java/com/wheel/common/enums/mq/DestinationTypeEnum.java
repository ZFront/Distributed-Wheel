package com.wheel.common.enums.mq;

import java.util.Arrays;


/**
 * @description
 * @author: zhouf
 * @date: 2020/5/28
 */
public enum DestinationTypeEnum {

    QUEUE("QUEUE", 1),
    TOPIC("TOPIC", 2);

    DestinationTypeEnum(String desc, int value) {
        this.desc = desc;
        this.value = value;
    }

    /**
     * 描述
     */
    private String desc;
    /**
     * 枚举值
     */
    private int value;

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

    public static DestinationTypeEnum getEnum(int value) {
        return Arrays.stream(values()).filter(p -> p.value == value).findFirst().orElse(null);
    }


}
