package com.wheel.service.lock.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

/**
 * 操作 zookeeper 客户端
 * 采用 Curator 实现，Curator 里面已经实现对应的分布式锁，
 *
 * @author: zhouf
 */
@Slf4j
public class ZKClient {

    private CuratorFramework curatorClient;

    /**
     * 锁路径前缀
     */
    private final static String LOCK_PATH_PREFIX = "/locks";

    public ZKClient(CuratorFramework curator) {
        curatorClient = curator;
        curator.start();
        log.info("启动zk客户端 success...");
    }

    /**
     * 分布式可重入锁
     *
     * @param path
     * @return
     */
    public InterProcessMutex getReentrantLock(String path) {
        return new InterProcessMutex(curatorClient, formatPath(path));
    }

    private String formatPath(String path) {
        return path.startsWith("/") ? LOCK_PATH_PREFIX + path : LOCK_PATH_PREFIX + "/" + path;
    }
}

