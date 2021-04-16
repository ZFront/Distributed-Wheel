package com.wheel.gateway.ratelimit.example;

/**
 * @desc {@see <https://www.infoq.cn/article/qg2tx8fyw5vt-f3hh673>}
 * 限流实现方法 - 简易实现
 * 令牌桶
 * @author: zhouf
 */
public class TokenBucket {

    /**
     * 时间
     */
    private long time;
    /**
     * 总量
     */
    private Double total;
    /**
     * token 放入速度
     */
    private Double rate;
    /**
     * 当前总量
     */
    private Double nowSize;

    public boolean limit() {
        long now = System.currentTimeMillis();
        nowSize = Math.min(total, nowSize + (now - time) * rate);
        time = now;
        if (nowSize < 1) {
            // 桶里没有token
            return false;
        } else {
            // 存在token
            nowSize -= 1;
            return true;
        }
    }

    public static void main(String[] args) {
        
    }
}
