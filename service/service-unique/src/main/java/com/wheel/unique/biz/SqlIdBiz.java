package com.wheel.unique.biz;

import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.Status;
import com.wheel.common.enums.exception.BizCodeEnum;
import com.wheel.common.exception.BizException;
import com.wheel.unique.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
            throw new BizException(BizCodeEnum.BIZ_ERROR.getCode(), "segment生成失败,key=" + key);
        }
    }

    /**
     * 数据库发号
     * TIPS：其实最好的实现方式是
     *
     * @param key
     * @param count
     * @return
     */
    public List<Long> getId(String key, int count) {
        List<Long> idList = new ArrayList<>(count);
        while (count-- > 0) {
            idList.add(getId(key));
        }
        return idList;
    }
}
