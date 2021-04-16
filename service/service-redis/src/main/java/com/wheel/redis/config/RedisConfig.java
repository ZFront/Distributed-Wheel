package com.wheel.redis.config;

import com.wheel.redis.core.RedisConnectionFactory;
import com.wheel.redis.prop.RedisProp;
import com.wheel.redis.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
@EnableConfigurationProperties(RedisProp.class)
public class RedisConfig {

    @Autowired
    private RedisProp redisProp;

    @Bean
    @ConditionalOnMissingBean(JedisConnectionFactory.class)
    public JedisConnectionFactory jedisConnectionFactory() {
        return RedisConnectionFactory.init(redisProp);
    }


    @Bean
    @ConditionalOnMissingBean(RedisClient.class)
    public RedisClient redisClient(JedisConnectionFactory connectionFactory) {
        return new RedisClient(connectionFactory);
    }
}
