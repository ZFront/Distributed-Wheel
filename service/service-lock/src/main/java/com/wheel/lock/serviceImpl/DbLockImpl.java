package com.wheel.lock.serviceImpl;

import com.wheel.lock.core.DbLock;
import com.wheel.lock.service.LockFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description
 * @author: zhouf
 * @date: 2020/7/2
 */

public class DbLockImpl implements LockFacade {

    @Autowired
    private DbLock dbLock;

    @Override
    public String tryLock(String resourceId, int expireSecond) {
        return dbLock.tryLock(resourceId, expireSecond);
    }

    @Override
    public boolean unLock(String resourceId, String clientLockId) {
        return dbLock.unlock(resourceId, clientLockId);
    }

    @Override
    public boolean unlockForce(String resourceId, boolean isNeedDelete) {
        return dbLock.unlockForce(resourceId, isNeedDelete);
    }
}
