package com.wheel.basis.concurrency.locks;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @desc
 * @author: zhouf
 */
public class JavaLocksDemo {

    // ---------------------------- 悲观锁调用方式 ------------------------
    // synchronized
    public synchronized void testMethod() {
        // 操作同步资源
    }

    // ReentrantLock 默认非公平锁
    private ReentrantLock lock = new ReentrantLock();

    public void reentrantLockTest() {
        lock.lock();
        try {
            // 操作同步资源
        } finally {
            lock.unlock();
        }
    }

    // ---------------------------- 乐观锁调用方式 ------------------------
    // 需要保证多个线程使用同一个atomicInteger
    private AtomicInteger atomicInteger = new AtomicInteger();
    public void atomicTest() {
        atomicInteger.getAndIncrement();
    }

}
