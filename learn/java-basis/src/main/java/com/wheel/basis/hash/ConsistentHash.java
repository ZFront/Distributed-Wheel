package com.wheel.basis.hash;

import lombok.extern.slf4j.Slf4j;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @desc 一致性hash demo , 不带虚拟节点，可能会产生环偏斜
 * 一致性hash算法其实有很多种，这里选取了其中一种
 * @author: zhouf
 */
@Slf4j
public class ConsistentHash {

    /**
     * 待加入Hash环的服务器列表
     */
    private static String[] servers = {"192.168.0.1:8888", "192.168.0.2:8888",
            "192.168.0.3:8888", "192.168.0.4:8888"};

    /**
     * key表示服务器的hash值，value表示服务器
     */
    private static SortedMap<Integer, String> sortedMap = new TreeMap<>();

    /**
     * 初始化，把所有的服务器怼入sortedMap中
     */
    static {
        for (String service : servers) {
            int hash = getHash(service);
            log.info("{}加入集合中，其hash为：{}", service, hash);
            sortedMap.put(hash, service);
        }
    }


    /**
     * 使用FNV1_32_HASH算法计算服务器的Hash值
     *
     * @return
     */
    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }

        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }

    /**
     * 得到路由的节点
     *
     * @param key
     * @return
     */
    private static String getServer(String key) {
        int hash = getHash(key);
        //得到大于该Hash值的所有Map
        SortedMap<Integer, String> subMap = sortedMap.tailMap(hash);
        if (subMap.isEmpty()) {
            //没有比这个key的hash值大的，就从第一个node开始
            Integer i = sortedMap.firstKey();
            return sortedMap.get(i);
        } else {
            //第一个Key就是顺时针过去离node最近的那个结点
            Integer i = subMap.firstKey();
            //返回对应的服务器
            return subMap.get(i);
        }
    }

    public static void main(String[] args) {
        String[] keys = {"zekee", "hylan", "leyoo"};
        for (String key : keys) {
            log.info("{} 的hash为{}，路由至节点：{}", key, getHash(key), getServer(key));
        }
    }
}
