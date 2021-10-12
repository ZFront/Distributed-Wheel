package com.wheel.learn.basis.concurrency.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @desc
 * @author: zhouf
 */
public class AtomicIntegerDemo {

    public static volatile int count = 0; // 计数器
    public static final int size = 10000; // 循环测试次数

    /**
     * @param args
     */
    public static void main(String[] args) {

        // 可以看到volatile在多写的环境下，是非线程安全的
        Thread thread = new Thread(() -> {
            for (int i = 1; i <= size; i++) {
                count++;
            }
        });
        thread.start();
        for (int i = 1; i <= size; i++) {
            count--;
        }
        // 等所有线程执行完成
        while (thread.isAlive()) {
        }
        System.out.println(count);


        AtomicInteger atomicInteger = new AtomicInteger();
        Thread thread1 = new Thread(() -> {
            for (int i = 1; i <= size; i++) {
                atomicInteger.getAndIncrement();
            }
        });
        thread1.start();

        // 等所有线程执行完成
        while (thread1.isAlive()) {
        }
        int atomicCount = atomicInteger.get();
        for (int i = 1; i <= size; i++) {
            atomicCount--;
        }
        System.out.println(atomicCount);

    }
}




