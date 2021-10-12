package com.wheel.facade.unique.service;

import java.util.List;

/**
 * @description 唯一索引接口
 * @author: zhouf
 * @date: 2020/7/29
 */
public interface UniqueFacade {

    /**
     * 通过leaf中的segment方式实现, 改写了leaf-core,实现没有key,也能生成对应的id
     *
     * @param key
     * @return
     */
    long getSegmentId(String key);

    /**
     * 数据库发号
     *
     * @param key
     * @param count 发号数量
     * @return
     */
    List<Long> getSegmentId(String key, int count);

    /**
     * 通过leaf中的snowFlake方式实现
     *
     * @param key
     * @return
     */
    long getSnowFlakeId(String key);

    /**
     * 依赖于redis高可用 ==> incrBy指令
     * 最大ID为10E, 超过则直接重置
     *
     * @param key
     * @return
     */
    long getRedisId(String key);

    /**
     * @param key
     * @param incrStep 增长步长
     * @return
     */
    long getRedisId(String key, int incrStep);

    /**
     * 返回大写的UUID
     *
     * @return
     */
    String getUUID();

}
