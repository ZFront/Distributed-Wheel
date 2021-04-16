package com.wheel.lock;

import com.wheel.common.util.StringUtil;
import com.wheel.lock.core.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @desc
 * @author: zhouf
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceLockApp.class)
public class RedisLockTest {

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private RedissonClient client;

    /**
     * 上锁
     */
    @Test
    public void tryLock() {
        String resourceId = StringUtil.getUUIDStr();
        // 上锁
        RLock lock = redisLock.tryLock(resourceId, 5, 30);
        try {
            if (lock != null) {
                // 做业务处理
                System.out.println("开始业务处理");
            } else {
                System.out.println("锁获取失败");
            }
        } finally {
            // 释放锁
            if (lock != null) {
                // 释放
                redisLock.unlock(lock);
                System.out.println("释放锁, resourceId=" + resourceId);
            }
        }
    }

    /**
     * 可重入锁写法
     */
    public void reentrantLock() {
        String resourceId = StringUtil.getUUIDStr();
        RLock lock = client.getLock(resourceId);
        try {
            lock.lock();

            // 做业务处理
            System.out.println("开始业务处理");

            lock.lock();
        } finally {
            lock.lock();
            lock.lock();
        }
    }
}
