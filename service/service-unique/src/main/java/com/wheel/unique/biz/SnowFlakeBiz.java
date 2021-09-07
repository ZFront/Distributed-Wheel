package com.wheel.unique.biz;

import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.Status;
import com.wheel.common.enums.exception.PublicBizCodeEnum;
import com.wheel.common.exception.BizException;
import com.wheel.unique.service.SnowflakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description 雪花算法 == > 实现方式: 采用美团leaf中集成的实现
 * @author: zhouf
 * @date: 2020/7/24
 */
@Component
public class SnowFlakeBiz {

    @Autowired
    private SnowflakeService snowflakeService;

    /**
     * @param key
     * @return
     */
    public long getId(String key) {
        Result result = snowflakeService.getId(key);
        if (result.getStatus() == Status.SUCCESS) {
            return result.getId();
        } else {
            throw new BizException(PublicBizCodeEnum.BIZ_ERROR.getCode(), "snowFlake生成失败,key=" + key);
        }
    }
}
