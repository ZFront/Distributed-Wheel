package com.wheel.facade.lock.entity;

/**
 * @description locker 状态枚举
 * @author: zhouf
 * @date: 2020/7/1
 */
public enum LockerStatusEnum {

    /**
     * 空闲
     */
    FREE(1, "空闲"),

    /**
     * 上锁
     */
    LOCKING(2, "上锁"),
    ;

    private int value;
    private String desc;

    LockerStatusEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

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
