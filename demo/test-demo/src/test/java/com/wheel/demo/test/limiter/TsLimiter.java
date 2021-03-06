package com.wheel.demo.test.limiter;

import com.wheel.common.util.OkHttpUtil;
import com.wheel.demo.test.TestDemoApplication;
import com.wheel.service.redis.ratelimit.core.RedisLimiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        ExecutorService executorService = Executors.newFixedThreadPool(50);

        for (int i = 0; i < 50; i++) {
            executorService.submit(() -> {
                if (!redisLimiter.tryAcquire("testLimiter", 5, 10)) {
                    System.out.println("服务器繁忙");
                } else {
                    System.out.println("get job");
                }
            });
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
