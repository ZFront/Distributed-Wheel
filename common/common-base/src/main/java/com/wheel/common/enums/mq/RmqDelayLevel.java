package com.wheel.common.enums.mq;

/**
 * @description rockmq 延时等级
 * rockMq 不支持精确延时
 * @author: zhouf
 * @date: 2020/6/3
 */
public enum RmqDelayLevel {
    s_1(1, "1s"),
    s_5(2, "5s"),
    s_10(3, "10s"),
    s_30(4, "30s"),
    m_1(5, "1m"),
    m_2(6, "2m"),
    m_3(7, "3m"),
    m_4(8, "4m"),
    m_5(9, "5m"),
    m_6(10, "6m"),
    m_7(11, "7m"),
    m_8(12, "8m"),
    m_9(13, "9m"),
    m_10(14, "10m"),
    m_20(15, "20m"),
    m_30(16, "30m"),
    h_1(17, "1h"),
    h_2(18, "2h"),
    ;

    /**
     * 枚举值
     */
    private int value;
    /**
     * 描述
     */
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


    RmqDelayLevel(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
