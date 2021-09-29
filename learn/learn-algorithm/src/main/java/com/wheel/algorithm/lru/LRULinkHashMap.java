package com.wheel.algorithm.lru;

import java.util.LinkedHashMap;

/**
 * @desc LRU 算法的 linkHashMap 实现
 * 链表头元素即是最久未使用，尾元素则是最近有使用的元素
 * @author: zhouf
 */
public class LRULinkHashMap {

    int cap;

    LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>();

    public LRULinkHashMap(int capacity) {
        this.cap = capacity;
    }

    /**
     * get 操作 ==> 将key变为最近使用
     * @param key
     * @return
     */
    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        // 将 key 变为最近使用
        makeRecently(key);
        return cache.get(key);
    }

    /**
     * put 操作 ==> 已存在的变为最近使用，超过容量的，移除
     * @param key
     * @param val
     */
    public void put(int key, int val) {
        // 存在值
        if (cache.containsKey(key)) {
            // 修改 key 的值
            cache.put(key, val);
            // 将 key 变为最近使用
            makeRecently(key);
            return;
        }

        // 超过容量，就移除最久未使用
        if (cache.size() >= this.cap) {
            // 链表头部就是最久未使用的 key
            int oldestKey = cache.keySet().iterator().next();
            cache.remove(oldestKey);
        }
        // 将新的 key 添加链表尾部
        cache.put(key, val);
    }

    public static void main(String[] args) {
        LRULinkHashMap lru = new LRULinkHashMap(2);

        lru.put(1, 1);
        lru.put(2, 2);
        lru.put(3, 3);

        // 此时容器已满，(1,1) 会被移出去
        System.out.println(lru.get(1));

        System.out.println(lru.get(2));
    }

    /**
     * 变为最近使用
     *
     * @param key
     */
    private void makeRecently(int key) {
        int val = cache.get(key);
        // 删除 key，重新插入到队尾
        cache.remove(key);
        cache.put(key, val);
    }
}
