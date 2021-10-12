package com.wheel.demo.test.redis;

import com.wheel.demo.test.TestDemoApplication;
import com.wheel.service.redis.client.RedisClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description
 * @author: zhouf
 * @date: 2020/6/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestDemoApplication.class)
public class TsRedis {

    @Autowired
    private RedisClient redisClient;

    @Test
    public void testRedis() {
        String key = "flag";
        redisClient.set(key,"test");
        System.out.println(redisClient.get(key));

        redisClient.del(key);
        System.out.println(redisClient.get(key));
    }
}
