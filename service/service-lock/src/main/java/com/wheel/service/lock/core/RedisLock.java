package com.wheel.service.lock.core;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @desc redis 分布式锁实现
 *
 * @author: zhouf
 */
@Slf4j
@Component
public class RedisLock {

    @Autowired
    private RedissonClient client;

    /**
     * 尝试上锁
     *
     * @param resourceId   资源ID
     * @param waitSecond   等待时间
     * @param expireSecond 超时时间
     * @return
     */
    public RLock tryLock(String resourceId, int waitSecond, int expireSecond) {
        RLock lock = client.getLock(resourceId);
        try {
            if (lock.tryLock(waitSecond, expireSecond, TimeUnit.SECONDS)) {
                return lock;
            }
        } catch (Exception e) {
            log.error("resourceId={} 上锁出现异常, e=", resourceId, e);
        }
        return null;
    }

    /**
     * 释放锁：释放锁的线程和加锁的线程需要是同一个
     *
     * @param resourceId
     */
    public void unlock(String resourceId) {
        RLock lock = client.getLock(resourceId);
        lock.unlock();
    }

    /**
     * 释放锁
     *
     * @param lock
     */
    public void unlock(RLock lock) {
        lock.unlock();
    }

    /**
     * 强制释放锁
     *
     * @param resourceId
     */
    public void unlockForce(String resourceId) {
        RLock lock = client.getLock(resourceId);
        lock.forceUnlock();
    }

    /**
     * 强制释放锁
     *
     * @param lock
     */
    public void unlockForce(RLock lock) {
        lock.forceUnlock();
    }
}
