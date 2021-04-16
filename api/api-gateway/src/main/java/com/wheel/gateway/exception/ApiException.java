package com.wheel.gateway.exception;

import com.wheel.common.enums.api.ApiBizCodeEnum;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @description api 网关异常类
 * @author: zhouf
 */
@Getter
@Setter
public class ApiException extends RuntimeException implements Serializable {

    private String bizCode;

    private String bizMsg;

    private ApiException() {
        super();
    }

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(ApiBizCodeEnum exceptionEnum, String customMsg) {
        super(exceptionEnum.getMsg() + customMsg);

        this.bizCode = exceptionEnum.getCode();
        this.bizMsg = StringUtils.isBlank(customMsg) ? exceptionEnum.getMsg() : (exceptionEnum.getMsg() + "[" + customMsg + "]");
    }


    public ApiException(String msg, Throwable t) {
        super(msg, t);
    }

}
