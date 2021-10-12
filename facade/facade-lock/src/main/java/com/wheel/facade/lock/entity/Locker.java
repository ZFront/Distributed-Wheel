package com.wheel.facade.lock.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @description
 * @author: zhouf
 * @date: 2020/6/10
 */
@Getter
@Setter
public class Locker implements Serializable {

    private long id;
    private long version;
    private Date createTime = new Date();
    private Date updateTime;
    /**
     * 资源id
     */
    private String resourceId;
    /**
     * 资源状态
     * {@link LockerStatusEnum}
     */
    private Integer resourceStatus;
    /**
     * 客户端当前锁id(锁持有者)
     */
    private String clientLockId;
    /**
     * 上锁时间
     */
    private Date lockTime;
    /**
     * 过期时间(NULL表示永不过期)
     */
    private Date expireTime;
}
