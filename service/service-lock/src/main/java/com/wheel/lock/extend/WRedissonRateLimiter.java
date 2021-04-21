package com.wheel.lock.extend;

import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;

/**
 * @desc redisson 限流
 * 基于lua实现：{@link com.wheel.redis.ratelimit.core.RedisLimiter}
 * @author: zhouf
 */
public class WRedissonRateLimiter {

    private RedissonClient redissonClient;

    public WRedissonRateLimiter(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 尝试获取令牌
     *
     * @param key
     * @param rate
     * @return
     */
    public boolean tryAcquire(String key, int rate) {

        // 创建对应的限流器
        RRateLimiter rateLimiter = createLimiter(key, rate);

        return rateLimiter.tryAcquire();
    }

    private RRateLimiter createLimiter(String key, int rate) {

        // 创建对应的限流器
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);

        // 每1秒产生rate个令牌
        rateLimiter.trySetRate(RateType.OVERALL, rate, 1, RateIntervalUnit.SECONDS);

        return rateLimiter;
    }
}
