package com.wheel.api.gateway.ratelimit.example;

/**
 * @desc {@see <https://www.infoq.cn/article/qg2tx8fyw5vt-f3hh673>}
 * 限流实现方法 - 简易实现
 * 漏桶算法
 * @author: zhouf
 */
public class LeakBucket {
    /**
     * 时间
     */
    private long time;
    /**
     * 总量
     */
    private Double total;
    /**
     * 水流出去的速度
     */
    private Double rate;
    /**
     * 当前总量
     */
    private Double nowSize;

    public boolean limit() {
        long now = System.currentTimeMillis();
        nowSize = Math.max(0, (nowSize - (now - time) * rate));
        time = now;
        if ((nowSize + 1) < total) {
            nowSize++;
            return true;
        } else {
            return false;
        }

    }

    public static void main(String[] args) {
        
    }
}
