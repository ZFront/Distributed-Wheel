package com.wheel.demo.limiter;

import com.wheel.common.util.OkHttpUtil;
import com.wheel.demo.TestDemoApplication;
import com.wheel.redis.ratelimit.core.RedisLimiter;
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
@SpringBootTest(classes = TestDemoApplication.class)
public class TsLimiter {

    @Autowired
    private RedisLimiter redisLimiter;

    @Test
    public void testRedisLimit() {
        for (int i = 0; i < 100; i++) {
            if (!redisLimiter.tryAcquire("testLimiter", 1, 2)) {
                System.out.println("服务器繁忙");
            } else {
                System.out.println("get job");
            }
        }
    }

    @Test
    public void testGuavaLimit() throws Exception {
        for (int i = 0; i < 100; i++) {
            String body = OkHttpUtil.getSync("http://127.0.0.1:9001/limiter/limit", null, null);
            System.out.println(body);
        }
    }
}
