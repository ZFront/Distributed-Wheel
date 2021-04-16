package com.wheel.common.enums.timer;

/**
 * @description
 * @author: zhouf
 * @date: 2020/7/13
 */
public enum TimeUnitEnum {
    /**
     * 毫秒
     */
    MILL_SECOND(1, "毫秒"),
    /**
     * 秒
     */
    SECOND(2, "秒"),
    /**
     * 分
     */
    MINUTE(3, "分"),
    /**
     * 小时
     */
    HOUR(4, "小时");

    TimeUnitEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private int value;
    private String desc;

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
}
