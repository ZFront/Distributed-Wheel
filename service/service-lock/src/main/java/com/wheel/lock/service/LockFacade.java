package com.wheel.lock.service;

import com.wheel.common.exception.BizException;

/**
 * @description 分布式锁
 * @author: zhouf
 * @date: 2020/6/5
 */
public interface LockFacade {

    /**
     * 尝试上锁：如果得到了锁，会返回一个字符串，视为clientId，解锁的时候就需要传入此标识，如果获取不到锁，就返回null
     *
     * @param resourceId   资源id，一个资源的唯一标志符
     * @param expireSecond 超时时间(秒)，超过时间后将自动释放锁，避免死锁，当为-1时表示永不超时，但可能会造成死锁
     * @return clientLockId 当前客户端锁id
     * @throws BizException
     */
    String tryLock(String resourceId, int expireSecond) throws BizException;

    /**
     * 释放锁，释放成功返回true,失败会返回false
     *
     * @param resourceId
     * @param clientLockId
     * @return
     * @throws BizException
     */
    boolean unLock(String resourceId, String clientLockId) throws BizException;

    /**
     * 强制解锁：不管锁是不是正在被持有，是否超时，都强行释放，主要是可以对一些已经查明的死锁进行解锁
     *
     * @param resourceId
     * @param isNeedDelete 释放资源时，是否需要删除对应的资源记录
     * @return
     * @throws BizException
     */
    boolean unlockForce(String resourceId, boolean isNeedDelete) throws BizException;
}
