package com.wheel.common.vo.api;

import com.wheel.common.enums.api.RespCodeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @description 返回实体
 * @author: zhouf
 * @date: 2020/9/9
 */
@Getter
@Setter
public class ResponseParam implements Serializable {

    /**
     * 响应结果
     * {@link RespCodeEnum}
     */
    private String resp_code;

    /**
     * 响应描述
     */
    private String resp_msg;

    /**
     * 结果
     */
    private String data;

    public static ResponseParam acceptUnknown() {
        ResponseParam responseParam = new ResponseParam();
        responseParam.setResp_code(RespCodeEnum.UNKNOWN.name());
        responseParam.setResp_msg("未知结果");
        responseParam.setData("");
        return responseParam;
    }
}
