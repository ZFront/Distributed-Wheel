package com.wheel.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @description 业务异常类
 * @author: zhouf
 * @date: 2020/5/29
 */
@Getter
@Setter
public class BizException extends RuntimeException implements Serializable {
    private String bizCode;

    private String bizMsg;

    public BizException(String bizCode, String bizMsg) {
        super(bizMsg);
        this.bizCode = bizCode;
        this.bizMsg = bizMsg;
    }


    public BizException(String bizCode, String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(String bizMsg, Throwable cause) {
        super(bizMsg, cause);
        this.bizMsg = bizMsg;
    }

    public BizException(String bizMsg) {
        super(bizMsg);
        this.bizMsg = bizMsg;
    }

    public BizException(Throwable cause) {
        super(cause);
    }
}
