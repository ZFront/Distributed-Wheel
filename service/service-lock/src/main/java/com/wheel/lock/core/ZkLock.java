package com.wheel.lock.core;

import com.wheel.common.exception.BizException;
import com.wheel.lock.client.ZKClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @desc zookeeper 分布式锁
 * 可靠性: zk > redis
 * 性能： zk < redis
 * 采用Apache curator 中提供的相关锁来实现zk分布式锁
 * @author: zhouf
 */
@Slf4j
@Component
public class ZkLock {

    @Autowired
    private ZKClient zkClient;

    /**
     * 采用 {@link InterProcessMutex} 全局可重入锁
     * 在ZK锁中，不需要锁超时时间，因为zk session 连接断开，锁会自动失效
     *
     * @param resourceId 资源id
     * @param waitSecond 等待时间
     */
    public InterProcessMutex tryLock(String resourceId, int waitSecond) {
        InterProcessMutex lock = zkClient.getReentrantLock(resourceId);
        try {
            if (lock.acquire(waitSecond, TimeUnit.SECONDS)) {
                return lock;
            }
        } catch (Exception e) {
            log.error("resourceId={} 上锁失败", resourceId);
        }
        return null;
    }

    /**
     * 释放锁，释放锁的线程和加锁的线程必须是同一个才能进行释放。
     *
     * @param lock
     */
    public void unlock(InterProcessMutex lock) {
        try {
            lock.release();
        } catch (Exception e) {
            log.error("释放锁失败", e);
            throw new BizException("释放锁失败", e);
        }
    }

    /**
     * 强制释放锁
     * 但 zk不支持强制释放锁，加锁和释放锁的必须是同一个线程才可以
     *
     * @param lock
     */
    public void unlockForce(InterProcessMutex lock) {
        if (lock.isOwnedByCurrentThread()) {
            try {
                lock.release();
            } catch (Exception e) {
                log.error("释放锁失败", e);
                throw new BizException("释放锁失败", e);
            }
        } else {
            throw new BizException("zk未支持强制释放锁, 加锁和释放锁必须为同一个线程");
        }
    }
}
