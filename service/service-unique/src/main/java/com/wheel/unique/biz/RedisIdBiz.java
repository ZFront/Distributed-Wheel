package com.wheel.unique.biz;

import com.wheel.redis.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description redis 自动生成
 * @author: zhouf
 * @date: 2020/7/24
 */
@Component
public class RedisIdBiz {

    @Autowired
    private RedisClient redisClient;

    public final static int DEFAULT_REDIS_MAX_ID = 999999999;

    /**
     * 获取递增ID
     * 当超过默认的最大值，会重置
     *
     * @param key
     * @return
     */
    public long getId(String key) {
        return redisClient.loopIncrId(key, 1, DEFAULT_REDIS_MAX_ID);
    }

    public long getId(String key, int incrStep) {
        return redisClient.loopIncrId(key, incrStep, DEFAULT_REDIS_MAX_ID);
    }
}
