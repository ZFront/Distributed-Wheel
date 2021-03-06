package com.wheel.learn.basis.concurrency.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @desc
 * @author: zhouf
 */
public class ReentrantLockDemo {

    public static void main(String[] args) throws Exception {
        // 初始化，可以选择公平锁、非公平锁
        ReentrantLock lock = new ReentrantLock(true);

        // 可重入锁
        lock.lock();
        try {
            try {
                // 支持多种加锁方式，比较灵活; 具有可重入特性
                if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                    // 执行业务操作
                }
            } finally {
                lock.unlock();
            }
        } finally {
            lock.unlock();
        }
    }
}
