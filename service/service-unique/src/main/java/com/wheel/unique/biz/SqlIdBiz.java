package com.wheel.unique.biz;

import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.Status;
import com.wheel.common.enums.exception.PublicBizCodeEnum;
import com.wheel.common.exception.BizException;
import com.wheel.unique.service.impl.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description
 * SQL实现方式 ==> 美团leaf集成的方式
 *
 * @author: zhouf
 * @date: 2020/7/24
 */
@Component
public class SqlIdBiz {

    @Autowired
    private SegmentService segmentService;

    /**
     * @param key
     * @return
     */
    public long getId(String key) {
        Result result = segmentService.getId(key);
        if (result.getStatus() == Status.SUCCESS) {
            return result.getId();
        } else {
            throw new BizException(PublicBizCodeEnum.BIZ_ERROR.getCode(), "segment生成失败,key=" + key);
        }
    }
}
