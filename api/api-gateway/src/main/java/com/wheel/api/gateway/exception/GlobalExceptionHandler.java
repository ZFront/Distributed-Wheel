package com.wheel.api.gateway.exception;

import com.wheel.common.enums.api.RespCodeEnum;
import com.wheel.common.exception.BizException;
import com.wheel.common.vo.api.ResponseParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @description 统一异常处理类。将抛出的异常，封装成统一的Json格式
 * @author: zhouf
 */
@Slf4j
public class GlobalExceptionHandler extends DefaultErrorWebExceptionHandler {

    /**
     * 默认构造器
     *
     * @param errorAttributes
     * @param resourceProperties
     * @param errorProperties
     * @param applicationContext
     */
    public GlobalExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    /**
     * {@link DefaultErrorWebExceptionHandler} 原方法，返回的是html页面
     * ==> 改成支持Json
     *
     * @param errorAttributes
     * @return
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        boolean includeStackTrace = isIncludeStackTrace(request, MediaType.ALL);
        Throwable t = getError(request);
        Map<String, Object> error = getErrorAttributes(request, includeStackTrace);
        ResponseParam response = this.buildResponseParam(t);
        return ServerResponse.status(getHttpStatus(error)).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(response));
    }

    private ResponseParam buildResponseParam(Throwable t) {
        ResponseParam response = new ResponseParam();

        /**
         * 网关层抛出的异常
         */
        if (t instanceof ApiException) {
            ApiException ex = (ApiException) t;
            response.setResp_code(RespCodeEnum.FAIL.name());
            response.setResp_msg(ex.getBizMsg());
        }

        /**
         * 超时异常，可能请求到了服务，也可能没有请求到。
         */
        if (t instanceof TimeoutException) {
            response.setResp_code(RespCodeEnum.UNKNOWN.name());
            response.setResp_msg("request time out");
        }

        /**
         * 基础业务exception
         */
        if (t instanceof BizException) {
            BizException ex = (BizException) t;
            response.setResp_code(RespCodeEnum.FAIL.name());
            response.setResp_msg(ex.getBizMsg());
        }
        return response;
    }
}
