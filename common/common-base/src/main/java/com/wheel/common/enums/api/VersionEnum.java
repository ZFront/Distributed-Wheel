package com.wheel.common.enums.api;

import java.util.Arrays;

/**
 * @description 版本枚举
 * 方改后续出现版本分叉时，用于接口方便做版本判断
 * @author: zhouf
 * @date: 2020/10/22
 */
public enum VersionEnum {

    /**
     * 1.0版本
     */
    V1_0("1.0", "V1.0"),

    V2_0("2.0", "V2.0");

    /**
     * 枚举值
     */
    private String value;
    /**
     * 描述
     */
    private String msg;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static VersionEnum getEnum(String value) {
        return Arrays.stream(values()).filter(p -> p.value.equals(value)).findFirst().orElse(null);
    }

    VersionEnum(String value, String desc) {
        this.value = value;
        this.msg = desc;
    }
}
