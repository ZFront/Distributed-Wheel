package com.wheel.common.enums.api;

/**
 * @description api 业务码
 * @author: zhouf
 * @date: 2020/9/25
 */
public enum ApiBizCodeEnum {

    /**
     * API 应答码，成功
     */
    SUCCESS("200", "成功"),
    FAIL("300", "失败"),
    SYSTEM_ERR("400", "系统异常"),
    ;

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ApiBizCodeEnum(String code, String desc) {
        this.code = code;
        this.msg = desc;
    }
}
