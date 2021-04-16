package com.wheel.common.enums.api;

import java.util.Arrays;
import java.util.List;

/**
 * @description 接口方法枚举
 * @author: zhouf
 * @date: 2020/10/22
 */
public enum MethodEnum {

    /**
     * demo：
     */
    DEMO_OK("demo.ok", "测试接口-OK"),

    DEMO_NO("demo.no", "测试接口-NO");

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

    public static MethodEnum getEnum(String value) {
        return Arrays.stream(values()).filter(p -> p.value.equals(value)).findFirst().orElse(null);
    }

    MethodEnum(String value, String desc) {
        this.value = value;
        this.msg = desc;
    }
}
