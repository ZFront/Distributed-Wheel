package com.wheel.basis.hash;

import lombok.extern.slf4j.Slf4j;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @desc 一致性hash demo，带有虚拟节点
 * @author: zhouf
 */
@Slf4j
public class ConsistenHashVirtualNode {

    /**
     * 待加入Hash环的服务器列表, 真实的服务器
     */
    private static String[] servers = {"192.168.0.1:8888", "192.168.0.2:8888",
            "192.168.0.3:8888", "192.168.0.4:8888"};

    private static SortedMap<Integer, String> virtualNodes = new TreeMap<>();

    /**
     * key表示服务器的hash值，value表示服务器
     */
    private static SortedMap<Integer, String> sortedMap = new TreeMap<>();
    /**
     * 每个服务器，生成的虚拟节点的个数。
     * 一般来说，这个虚拟的节点会比较大
     */
    private static int virtualNums = 3;

    /**
     * 初始化，把所有的服务器怼入sortedMap中
     */
    static {
        for (String service : servers) {
            addService(service);
        }
    }

    /**
     * 添加节点
     *
     * @param service
     */
    public static void addService(String service) {
        int hash = getHash(service);
        log.info("{}加入集合中，其hash为：{}", service, hash);
        sortedMap.put(hash, service);

        // 加入虚拟节点个数
        for (int i = 0; i < virtualNums; i++) {
            virtualNodes.put(getHash(service + "?vnode=" + i), service);
        }
    }

    /**
     * 移除节点 . ==> 缩容的时候使用
     *
     * @param service
     */
    public static void removeService(String service) {
        sortedMap.remove(getHash(service));
        for (int i = 0; i < virtualNums; i++) {
            virtualNodes.remove(getHash(service + "?vnode=" + i));
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
     * 获取路由的节点
     *
     * @return
     */
    private static String getServer(String key) {
        int hash = getHash(key);
        //得到大于该Hash值的所有虚拟节点
        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);
        if (subMap.isEmpty()) {
            //没有比这个key的hash值大的，就从第一个node开始
            Integer i = virtualNodes.firstKey();
            return virtualNodes.get(i);
        } else {
            //第一个Key就是顺时针过去离node最近的那个结点
            Integer i = subMap.firstKey();
            //返回对应的服务器
            return subMap.get(i);
        }
    }

    public static void main(String[] args) {
        String[] keys = {"刘邦", "张三", "赵云"};
        for (String key : keys) {
            log.info("{} 的hash为{}，路由至节点：{}", key, getHash(key), getServer(key));
        }
    }
}
