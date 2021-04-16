package com.wheel.common.enums.exception;

/**
 * @description 通用业务码枚举类
 * @author: zhouf
 * @date: 2020/6/9
 */
public enum PublicBizCodeEnum {

    /**
     * 数据库处理异常
     */
    DB_ERROR("P000", "数据库处理异常"),

    /**
     * 参数校验错误：如长度错误，未必填等
     */
    PARAM_INVALID("P001", "参数校验错误"),

    /**
     * 业务校验错误：如状态错误，信息错误等
     */
    BIZ_INVALID("P002", "业务校验错误"),

    /**
     * 业务流程异常：如异常中断，状态不合法等
     */
    BIZ_ERROR("P003", "业务流程异常"),

    /**
     * 系统异常
     */
    SYS_ERROR("P004", "系统异常"),
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

    PublicBizCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
