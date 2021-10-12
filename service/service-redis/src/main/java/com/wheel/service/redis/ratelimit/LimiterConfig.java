package com.wheel.service.redis.ratelimit;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @desc 本地限流配置器，仅适用于单体应用的限流
 * @author: zhouf
 */
public class LimiterConfig {

    /**
     * 每秒允许的数量
     */
    private static int PERMITS_PER_SECOND = 5;

    /**
     * guava限流器, 令牌桶算法
     * 仅适用于单体应用限流
     * <p>
     * 每秒发出60个令牌
     */
    public static RateLimiter rateLimiter = RateLimiter.create(PERMITS_PER_SECOND);

}
