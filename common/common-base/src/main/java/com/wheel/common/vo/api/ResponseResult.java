package com.wheel.common.vo.api;

import com.wheel.common.enums.api.RespCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @desc 对外接口响应Vo
 * 对应的实体未：{@link ResponseParam}
 * @author: zhouf
 */
@Setter
@Getter
public class ResponseResult<T> {

    private String respCode;
    private String respMsg;
    private T data;

    public static <T> ResponseResult<T> success() {
        return buildRespParam(RespCodeEnum.SUCCESS, RespCodeEnum.SUCCESS.getDesc(), null);
    }

    public static <T> ResponseResult<T> success(T data) {
        return buildRespParam(RespCodeEnum.SUCCESS, RespCodeEnum.SUCCESS.getDesc(), data);
    }

    public static <T> ResponseResult<T> fail() {
        return buildRespParam(RespCodeEnum.FAIL, RespCodeEnum.FAIL.getDesc(), null);
    }

    public static <T> ResponseResult<T> fail(T data) {
        return buildRespParam(RespCodeEnum.FAIL, RespCodeEnum.FAIL.getDesc(), data);
    }

    private static <T> ResponseResult<T> buildRespParam(RespCodeEnum respCode, String respMsg, T data) {
        ResponseResult<T> vo = new ResponseResult<>();
        vo.setRespCode(respCode.name());
        vo.setRespMsg(respMsg);
        vo.setData(data);
        return vo;
    }
}
