package com.wheel.service.lock;

import com.wheel.service.lock.extend.WRedissonRateLimiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @desc 限流器测试
 * @author: zhouf
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceLockApp.class)
public class RedissonRateLimitTest {

    @Autowired
    private WRedissonRateLimiter wRedissonRateLimiter;

    @Test
    public void testRedisLimit() {
        for (int i = 0; i < 50; i++) {
            if (wRedissonRateLimiter.tryAcquire("redissonRateLimiter", 5)) {
                System.out.println("get job");
            } else {
                System.out.println("服务繁忙");
            }
        }
    }
}
