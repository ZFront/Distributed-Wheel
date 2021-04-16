package com.wheel.lock.extend;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @desc
 * 基于Redisson实现的redis Bloom 过滤器
 * 不属于lock的业务，这里只是做一个知识点扩展
 * @author: zhouf
 */
@Component
public class RedisBloomFilter {

    @Autowired
    private RedissonClient redissonClient;

    private RBloomFilter<String> initRBloomFilter(String key) {
        RBloomFilter<String> rBloomFilter = redissonClient.getBloomFilter(key);
        // 预计统计数量为99999999, 希望误差率为0.03
        rBloomFilter.tryInit(99999999L, 0.03);
        return rBloomFilter;
    }

    public void add(String key, String value) {
        initRBloomFilter(key).add(value);
    }

    public void add(String key, String... values) {
        RBloomFilter<String> rBloomFilter = initRBloomFilter(key);
        for (String v : values) {
            rBloomFilter.add(v);
        }
    }

    public boolean mayContains(String key, String value) {
        return initRBloomFilter(key).contains(value);
    }

    /**
     * 只要有一个包含，就算包含
     *
     * @param key
     * @param values
     * @return
     */
    public boolean mayContains(String key, String... values) {
        RBloomFilter<String> rBloomFilter = initRBloomFilter(key);
        for (String v : values) {
            if (rBloomFilter.contains(v)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 统计存在数量
     *
     * @param key
     * @return
     */
    public long countNum(String key) {
        return initRBloomFilter(key).count();
    }
}
