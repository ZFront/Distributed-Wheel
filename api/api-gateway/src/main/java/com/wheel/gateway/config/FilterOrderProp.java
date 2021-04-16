package com.wheel.gateway.config;

/**
 * @description filter中的优先级配置
 * 数字越小，优先级越高
 * @author: zhouf
 * @date: 2020/9/7
 */
public class FilterOrderProp {

    // request 开始 ==>
    /**
     * 读取并过滤请求体，修正对应的请求体
     * {@link com.wheel.gateway.filters.global.RequestBodyFilter}
     */
    public final static int REQUEST_BODY_FILTER = -10;

    /**
     * 校验请求体参数是否正确
     * {@link com.wheel.gateway.filters.global.RequestCheckFilter}
     */
    public final static int REQUEST_CHECK_FILTER = -9;

    /**
     * 重写路径
     * {@link com.wheel.gateway.filters.global.RewritePathFilter}
     */
    public final static int REWRITE_PATH_FILTER = -8;

    // request 结束 ==>

    // response 开始 ==>
    public final static int RESPONSE_BUILD_FILTER = -5;

    // response 结束 ==>

}
