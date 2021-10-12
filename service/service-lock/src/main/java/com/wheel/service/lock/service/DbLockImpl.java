package com.wheel.service.lock.service;

import com.wheel.facade.lock.service.LockFacade;
import com.wheel.service.lock.core.DbLock;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description
 * @author: zhouf
 * @date: 2020/7/2
 */
@Service
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
