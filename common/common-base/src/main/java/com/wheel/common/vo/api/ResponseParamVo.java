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
public class ResponseParamVo<T> {

    private String respCode;
    private String respMsg;
    private T data;

    public static <T> ResponseParamVo<T> success() {
        return buildRespParam(RespCodeEnum.SUCCESS, RespCodeEnum.SUCCESS.getDesc(), null);
    }

    public static <T> ResponseParamVo<T> success(T data) {
        return buildRespParam(RespCodeEnum.SUCCESS, RespCodeEnum.SUCCESS.getDesc(), data);
    }

    public static <T> ResponseParamVo<T> fail() {
        return buildRespParam(RespCodeEnum.FAIL, RespCodeEnum.FAIL.getDesc(), null);
    }

    public static <T> ResponseParamVo<T> fail(T data) {
        return buildRespParam(RespCodeEnum.FAIL, RespCodeEnum.FAIL.getDesc(), data);
    }

    private static <T> ResponseParamVo<T> buildRespParam(RespCodeEnum respCode, String respMsg, T data) {
        ResponseParamVo<T> vo = new ResponseParamVo<>();
        vo.setRespCode(respCode.name());
        vo.setRespMsg(respMsg);
        vo.setData(data);
        return vo;
    }
}
