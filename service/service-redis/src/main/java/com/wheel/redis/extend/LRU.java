package com.wheel.redis.extend;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description redis Lru算法
 * 其中最常用的是<allkeys-lru> : 当内存不足容纳新写入的数据时，在键空间中，移除最近最少使用的key
 * lru : 移除最近最少使用的
 * random : 移除随机的key
 * ttl : 移除最早过期的key
 * @author: zhouf
 * @date: 2020/6/2
 */
public class LRU<K, V> extends LinkedHashMap<K, V> {
    private final int CACHE_SIZE;

    /**
     * 传递进来最多能缓存多少数据
     *
     * @param cacheSize 缓存大小
     */
    public LRU(int cacheSize) {
        // true 表示让 linkedHashMap 按照访问顺序来进行排序，最近访问的放在头部，最老访问的放在尾部。
        super((int) Math.ceil(cacheSize / 0.75) + 1, 0.75f, true);
        CACHE_SIZE = cacheSize;
    }

    /**
     * 通过put新增键值对的时候，若该方法返回true
     * 便移除该map中最老的键和值
     */
    @Override
    public boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // 当 map中的数据量大于指定的缓存个数的时候，就自动删除最老的数据。
        return super.size() > CACHE_SIZE;
    }
}
