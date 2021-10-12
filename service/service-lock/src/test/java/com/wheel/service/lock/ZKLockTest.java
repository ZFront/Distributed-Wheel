package com.wheel.service.lock;

import com.wheel.service.lock.client.ZKClient;
import com.wheel.service.lock.core.ZkLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @desc zk锁测试类
 * @author: zhouf
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceLockApp.class)
public class ZKLockTest {

    @Autowired
    private ZkLock zkLock;

    @Autowired
    private ZKClient zkClient;

    @Test
    public void tryLock() {
        String resourceId = "zkLockTest";
        InterProcessMutex lock = zkLock.tryLock(resourceId, 10);
        if (lock == null) {
            System.out.println("获取锁失败");
            return;
        }
        System.out.println("获得锁成功");

        // 释放锁
        zkLock.unlock(lock);
        System.out.println("释放锁成功");
    }

    @Test
    public void unlock() {
        // 直接释放锁的话，是不能成功的
        String resourceId = "zkLockTest";
        InterProcessMutex lock = zkClient.getReentrantLock(resourceId);
        zkLock.unlock(lock);
        System.out.println("释放锁成功");
    }

    @Test
    public void forceUnlock() {
        String resourceId = "zkLockTest";
        InterProcessMutex lock = zkClient.getReentrantLock(resourceId);
        zkLock.unlockForce(lock);
        System.out.println("释放锁成功");
    }
}
