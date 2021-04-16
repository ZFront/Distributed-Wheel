package com.wheel.redis.config;

import com.wheel.redis.client.RedisClient;
import com.wheel.redis.ratelimit.core.RedisLimiter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @desc redis 限流控制器
 * @author: zhouf
 */
@Configuration
public class RedisLimiterConfig {

    @Bean
    @ConditionalOnBean(RedisClient.class)
    @ConditionalOnMissingBean(RedisLimiter.class)
    public RedisLimiter redisLimiter(RedisClient redisClient) {
        return new RedisLimiter(redisClient);
    }
}
