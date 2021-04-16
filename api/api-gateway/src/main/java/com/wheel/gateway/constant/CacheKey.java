package com.wheel.gateway.constant;

import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;

/**
 * @description 用于{@link org.springframework.web.server.ServerWebExchange}中 Attributes缓存的key
 * 此类中的key主要用来存储对应的业务属性
 * 参考例子：{@link ServerWebExchangeUtils}
 * @author: zhouf
 */
public class CacheKey {

    /**
     * 重写后的地址
     */
    public static final String API_REWRITE_REQUEST_PATH = "rewriteRequestPath";

    /**
     * 请求的param
     */
    public static final String API_REQUEST_PARAM = "requestParam";

}
