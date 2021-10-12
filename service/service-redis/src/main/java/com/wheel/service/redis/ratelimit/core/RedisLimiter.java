package com.wheel.service.redis.ratelimit.core;

import com.wheel.service.redis.client.RedisClient;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @desc redis限流器
 * 参考实现 {@link org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter}
 * @author: zhouf
 */
public class RedisLimiter {

    private RedisClient redisClient;

    public RedisLimiter(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    /**
     * 获取令牌
     * 代码参考实现 {@link org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter#isAllowed}
     *
     * @param key  请求key
     * @param rate 每秒生成多少令牌
     * @param max  最大容量， 超过这个容量才会进行限制
     * @return
     */
    public boolean tryAcquire(String key, int rate, int max) {
        if (rate > max) {
            rate = max;
        }

        // 对应的key
        List<String> keys = getKeys(key);

        // 入参 rate: 令牌填充速率
        // max： 令牌桶大小
        // 当前时间：单位秒
        // 消耗令牌数：（默认为1）
        List<String> scriptArgs = Arrays.asList(rate + "", max + "", Instant.now().getEpochSecond() + "", "1");

        ArrayList<Long> results = redisClient.eval(LIMIT_SCRIPT, keys, scriptArgs);

        return results != null && results.get(0) == 1L;
    }

    private List<String> getKeys(String id) {
        // use `{}` around keys to use Redis Key hash tags
        // this allows for using redis cluster

        // Make a unique key per user.
        String prefix = "request_rate_limiter.{" + id;

        // You need two Redis keys for Token Bucket.
        String tokenKey = prefix + "}.tokens";
        String timestampKey = prefix + "}.timestamp";
        return Arrays.asList(tokenKey, timestampKey);
    }

    /**
     * 令牌桶算法, 使用spring-cloud-gateway项目中的request_rate_limiter.lua
     *  参数1：令牌填充速率； 参数2：令牌桶大小
     *  参数3：当前时间，单位秒； 参数4：消耗令牌数（默认为1）
     */
    public final static String LIMIT_SCRIPT = "" +
            // 获取剩余令牌数的redis key
            "local tokens_key = KEYS[1];" +
            // 获取最后一次填充令牌的时间
            "local timestamp_key = KEYS[2];" +

            // 令牌填充速率
            "local rate = tonumber(ARGV[1]);" +
            // 令牌桶大小
            "local capacity = tonumber(ARGV[2]);" +
            // 当前秒数
            "local now = tonumber(ARGV[3]);" +
            // 消耗令牌数，默认1
            "local requested = tonumber(ARGV[4]);" +
            // 计算令牌桶需要填充的时间
            "local fill_time = capacity/rate;" +
            // 计算key的存活时间
            "local ttl = math.floor(fill_time*2);" +
            // 获取剩余的令牌数
            "local last_tokens = tonumber(redis.call('get', tokens_key));" +
            "if last_tokens == nil then " +
            "  last_tokens = capacity;" +
            "end;" +
            // 获取令牌最后填充时间
            "local last_refreshed = tonumber(redis.call('get', timestamp_key));" +
            "if last_refreshed == nil then " +
            "  last_refreshed = 0;" +
            "end;" +

            "local delta = math.max(0, now-last_refreshed);" +
            // 计算得到剩余的令牌数
            "local filled_tokens = math.min(capacity, last_tokens+(delta*rate));" +
            // 大于请求消耗令牌， allowed为true
            "local allowed = filled_tokens >= requested;" +
            "local new_tokens = filled_tokens;" +
            "local allowed_num = 0;" +
            "if allowed then " +
            "  new_tokens = filled_tokens - requested;" +
            "  allowed_num = 1;" +
            "end;" +

            "redis.call('setex', tokens_key, ttl, new_tokens);" +
            "redis.call('setex', timestamp_key, ttl, now);" +

            "return { allowed_num, new_tokens };";
}
