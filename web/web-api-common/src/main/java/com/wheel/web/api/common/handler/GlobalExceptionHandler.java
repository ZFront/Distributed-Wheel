package com.wheel.web.api.common.handler;

import com.wheel.common.exception.BizException;
import com.wheel.common.vo.rest.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @desc 全局异常处理类
 * @author: zhouf
 */
@Slf4j
@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public RestResult exceptionHandle(BizException e) {
        logError(e);
        return RestResult.error(e.getBizMsg());
    }

    @ExceptionHandler(RpcException.class)
    public RestResult exceptionHandle(RpcException e) {
        logError(e);
        return RestResult.error("系统繁忙，请稍后重试！");
    }

    @ExceptionHandler(Exception.class)
    public RestResult exceptionHandle(Exception e) {
        logError(e);
        return RestResult.error("系统繁忙，请稍后重试！");
    }

    private void logError(Exception e) {
        log.error("异常栈：", e);
    }
}
