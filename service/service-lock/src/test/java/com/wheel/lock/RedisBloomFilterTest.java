package com.wheel.lock;

import com.wheel.lock.extend.RedisBloomFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @desc
 * @author: zhouf
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceLockApp.class)
public class RedisBloomFilterTest {

    @Autowired
    private RedisBloomFilter redisBloomFilter;

    @Test
    public void testBloomFilter() {
        String key = "rb";
        redisBloomFilter.add(key, "tom", "cat", "red", "dog");

        System.out.println(redisBloomFilter.mayContains(key, "tom"));

        System.out.println(redisBloomFilter.mayContains(key, "blue"));

        System.out.println(redisBloomFilter.countNum(key));
    }
}
