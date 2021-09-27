package com.wheel.common.vo.rest;

import java.util.Date;

/**
 * @desc 对内接口使用的返回实体
 * @author: zhouf
 */
public class RestResult<T> {

    // 系统异常
    public static final int SYS_ERROR = -1;
    // 成功
    public static final int SUCCESS = 1;
    // 业务异常
    public static final int BIZ_ERROR = 20001;
    // 参数校验错误
    public static final int PARAM_ERROR = 20002;

    private long code = SUCCESS;
    private String msg = "success";
    private Date date = new Date();
    private T data;

    private RestResult(T data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public static <T> RestResult<T> error(String message) {
        return new RestResult<>(null, BIZ_ERROR, message);
    }

    public static <T> RestResult<T> success(T data) {
        return new RestResult<>(data, SUCCESS, "");
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
