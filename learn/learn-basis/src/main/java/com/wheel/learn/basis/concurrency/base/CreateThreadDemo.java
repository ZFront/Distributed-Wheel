package com.wheel.learn.basis.concurrency.base;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @desc 创建线程
 * @author: zhouf
 */
public class CreateThreadDemo {

    public static void main(String[] args) {
        //1、继承Thread类，重写run方法
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("继承Thread");
                super.run();
            }
        };
        thread.start();

        //2、实现runnable接口
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("实现runnable接口");
            }
        });
        thread1.start();

        //lambda写法
        new Thread(() -> System.out.println("do something")).start();

        //3、实现callable接口
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> future = service.submit(() -> "通过callable实现接口");
        try {
            String result = future.get();
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e);
        }

        //4、使用Executors创建
        ExecutorService service1 = Executors.newSingleThreadExecutor();
        service1.execute(() -> System.out.println("使用Executors创建"));
        service1.shutdown();

        // 推荐用法
        ThreadFactory threadFactory = new ThreadNameFactory();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10,
                5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10), threadFactory);
        threadPoolExecutor.execute(() -> System.out.println("使用Executors创建"));
        threadPoolExecutor.shutdown();

    }

    static class ThreadNameFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, threadNumber.getAndIncrement() + "号员工");
        }
    }


}
