package com.wheel.lock.core;

import com.wheel.lock.constant.LockConstant;
import com.wheel.lock.entity.Locker;
import com.wheel.lock.dao.LockerDao;
import com.wheel.lock.entity.LockerStatusEnum;
import com.wheel.common.enums.exception.BizCodeEnum;
import com.wheel.common.exception.BizException;
import com.wheel.common.util.DateUtil;
import com.wheel.common.util.MD5Util;
import com.wheel.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @description
 * @author: zhouf
 * @date: 2020/6/5
 */
@Slf4j
@Component
public class DbLock {

    @Autowired
    private LockerDao lockerDao;

    /**
     * 尝试上锁
     * 没有锁记录 ==> 创建一条锁记录，并上锁
     * 以有锁记录 ==> 判断是否空闲状态 --> 判断是否过期
     *
     * @param resourceId   资源ID
     * @param expireSecond 过期时间
     * @return clientId 获取客户端id
     */
    @Transactional
    public String tryLock(String resourceId, int expireSecond) {
        if (StringUtil.isEmpty(resourceId)) {
            throw new BizException(BizCodeEnum.PARAM_INVALID.getCode(), "resourceId不能为空");
        } else if (resourceId.length() > 32) {
            throw new BizException(BizCodeEnum.PARAM_INVALID.getCode(), "resourceId长度不能超过32");
        } else if (expireSecond <= 0 && expireSecond != LockConstant.NEVER_EXPIRE_VALUE) {
            throw new BizException(BizCodeEnum.PARAM_INVALID.getCode(), "expireSecond 值无效");
        }

        Locker locker = lockerDao.getByResourceId(resourceId);

        try {
            // 如果没有记录，则新增一条并获得相关锁
            if (locker == null) {
                locker = new Locker();
                locker.setResourceId(resourceId);
                this.acquireLock(locker, expireSecond);
                lockerDao.insert(locker);
                return locker.getClientLockId();
            }

            if (LockerStatusEnum.FREE.getValue() == locker.getResourceStatus()) {
                // 锁状态为空闲，则表示能上锁
                this.acquireLock(locker, expireSecond);
            }

            if (locker.getExpireTime() == null) {
                // 如果锁过期时间为空，表示永不过期，则无法获得锁
                return null;
            }
            if (DateUtil.compare(locker.getExpireTime(), new Date(), Calendar.SECOND) > 0) {
                // 锁尚未过期，无法上锁
                return null;
            }

            // 锁已过期，可上锁
            this.acquireLock(locker, expireSecond);

            lockerDao.update(locker);
            return locker.getClientLockId();
        } catch (Exception e) {
            log.error("resourceId={},上锁失败", resourceId, e);
            return null;
        }
    }

    /**
     * 释放锁
     *
     * @param resourceId
     * @param clientLockId
     * @return
     */
    public boolean unlock(String resourceId, String clientLockId) {
        if (StringUtil.isEmpty(resourceId)) {
            throw new BizException(BizCodeEnum.PARAM_INVALID.getCode(), "resourceId不能为空");
        } else if (StringUtil.isEmpty(clientLockId)) {
            throw new BizException(BizCodeEnum.PARAM_INVALID.getCode(), "clientId不能为空");
        }

        Locker locker = lockerDao.getByResourceId(resourceId);
        if (locker == null) {
            return true;
        }

        if (LockerStatusEnum.FREE.getValue() == locker.getResourceStatus()) {
            return true;
        }

        if (!locker.getClientLockId().equals(clientLockId)) {
            return false;
        } else {
            // 释放锁
            this.releaseLock(locker);
        }

        try {
            lockerDao.update(locker);
            return true;
        } catch (Exception ex) {
            log.error("resourceId={},释放锁过程中数据库更新异常", resourceId);
            return false;
        }
    }

    /**
     * 强制释放锁
     *
     * @param resourceId
     * @param isDelete
     * @return
     */
    public boolean unlockForce(String resourceId, boolean isDelete) {
        if (StringUtil.isEmpty(resourceId)) {
            throw new BizException(BizCodeEnum.PARAM_INVALID.getCode(), "resourceId不能为空");
        }

        Locker locker = lockerDao.getByResourceId(resourceId);
        if (locker == null) {
            return true;
        }

        try {
            if (isDelete) {
                lockerDao.deleteById(locker.getId());
                return true;
            } else {
                this.releaseLock(locker);
                lockerDao.update(locker);
                return true;
            }
        } catch (Exception e) {
            log.error("resourceId={},强制释放锁过程中数据库更新异常", resourceId);
            return false;
        }
    }

    /**
     * 获取锁
     *
     * @param locker
     * @param expireSecond
     * @return
     */
    private Locker acquireLock(Locker locker, int expireSecond) {
        // TODO 后期增加唯一序列号后，可替换改ID
        String clientLockId = MD5Util.getMD5Hex(UUID.randomUUID().toString());
        Date now = new Date();
        locker.setResourceStatus(LockerStatusEnum.FREE.getValue());
        locker.setClientLockId(clientLockId);
        locker.setLockTime(now);
        if (expireSecond == LockConstant.NEVER_EXPIRE_VALUE) {
            locker.setExpireTime(null);
        } else {
            locker.setExpireTime(DateUtils.addSeconds(now, expireSecond));
        }
        return locker;
    }

    /**
     * 释放锁
     *
     * @param locker
     */
    private void releaseLock(Locker locker) {
        locker.setResourceStatus(LockerStatusEnum.FREE.getValue());
        locker.setClientLockId("");
        locker.setLockTime(null);
        locker.setExpireTime(null);
    }
}
