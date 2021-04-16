package com.wheel.gateway.ratelimit.example;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @desc {@see <https://www.infoq.cn/article/qg2tx8fyw5vt-f3hh673>}
 * 限流实现方法 - 简易实现
 * 固定窗口计数器: 控制单位时间内的请求数量
 * @author: zhouf
 */
public class Counter {
    /**
     * 最大访问数量
     */
    private final int limit = 10;
    /**
     * 访问时间差
     */
    private final long timeout = 1000;

    /**
     * 当前计数器
     */
    private AtomicInteger reqCount = new AtomicInteger(0);

    /**
     * 有个弊端就是窗口是固定的
     * 假设每分钟请求数量为 60 个,每秒可以处理 1 个请求,
     * 用户在 00:59 发送 60 个请求,在 01:00 发送 60 个请求
     * 此时 2 秒钟有 120 个请求(每秒 60 个请求),远远大于了每秒钟处理数量的阈值
     *
     * @return
     */
    public boolean limit(long reqTime) {
        long now = System.currentTimeMillis();
        if (now < reqTime + timeout) {
            // 单位时间内
            reqCount.addAndGet(1);
            return reqCount.get() <= limit;
        } else {
            // 超出单位时间
            reqTime = now;
            reqCount = new AtomicInteger(0);
            return true;
        }
    }

    public static void main(String[] args) {

    }
}
