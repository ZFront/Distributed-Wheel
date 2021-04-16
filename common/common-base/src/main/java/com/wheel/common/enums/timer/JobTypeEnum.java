package com.wheel.common.enums.timer;

import java.util.Arrays;

/**
 * @description
 * @author: zhouf
 * @date: 2020/7/13
 */
public enum JobTypeEnum {

    /**
     * {@link SimpleTrigger}
     */
    SIMPLE_JOB("简单任务", 1),

    /**
     * {@link CronTrigger}
     */
    CRON_JOB("CRON任务", 2);

    JobTypeEnum(String desc, int value) {
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

    public static JobTypeEnum getEnum(int value) {
        return Arrays.stream(values()).filter(p -> p.value == value).findFirst().orElse(null);
    }


}
