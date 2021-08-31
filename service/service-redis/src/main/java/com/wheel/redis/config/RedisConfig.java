package com.wheel.redis.config;

import com.wheel.redis.client.RedisClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;

@Configuration
@ConditionalOnClass({Jedis.class, JedisConnectionFactory.class})
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {

//    @Autowired
//    private RedisProp redisProp;
//
//    /**
//     * 自定义方式 -- 加强理解，实际可以不这么使用
//     * @return
//     */
//    @Bean
//    @ConditionalOnMissingBean(JedisConnectionFactory.class)
//    public JedisConnectionFactory jedisConnectionFactory() {
//        return RedisConnectionFactory.init(redisProp);
//    }


    @Bean
    @ConditionalOnMissingBean(RedisClient.class)
    public RedisClient redisClient(JedisConnectionFactory connectionFactory) {
        return new RedisClient(connectionFactory);
    }
}
