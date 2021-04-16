package com.wheel.common.enums.api;

/**
 * @description 响应码枚举
 * @author: zhouf
 * @date: 2020/9/10
 */
public enum RespCodeEnum {

    /**
     * 服务明确返回时
     */
    SUCCESS("受理成功"),
    /**
     * 参数校验、权限等未通过时
     */
    FAIL("受理失败"),

    /**
     * 因为熔断、降级等返回
     */
    UNKNOWN("受理未知");

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    RespCodeEnum(String desc) {
        this.desc = desc;
    }
}
