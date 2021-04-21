package com.wheel.lock.config;

import com.wheel.lock.core.RedissonClientFactory;
import com.wheel.lock.extend.WRedissonRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @desc redisson 自动装载
 * @author: zhouf
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RedisProp.class)
public class RedissonAutoConfig {

    @Autowired
    private RedisProp redisProp;

    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient createSentinel() {
        return RedissonClientFactory.init(redisProp);
    }

    @Bean
    @ConditionalOnBean(RedissonClient.class)
    public WRedissonRateLimiter redissonRateLimiter(RedissonClient redissonClient) {
        return new WRedissonRateLimiter(redissonClient);
    }
}
