package com.wheel.unique.biz;

import com.wheel.common.util.StringUtil;
import org.springframework.stereotype.Component;

/**
 * @description UUID
 * @author: zhouf
 * @date: 2020/7/24
 */
@Component
public class UuidBiz {

    /**
     * 获取默认规则的ID
     *
     * @return
     */
    public String getId() {
        return StringUtil.getUUIDStr().toUpperCase();
    }
}
