package com.wheel.basis.concurrency.base;

/**
 * @desc SynchronizedDemo
 * @author: zhouf
 */
public class SynchronizedDemo {

    // 同步方法
    public synchronized void hello() {
        System.out.println("hello");
    }

    // 同步代码块
    public void hi() {
        synchronized (SynchronizedDemo.class) {
            System.out.println("hi");
        }
    }

    // 重入锁
    public synchronized void doSomething() {
        System.out.println("doSomething");
        doOthers();
    }

    public synchronized void doOthers() {
        System.out.println("doOthers");
    }

}
