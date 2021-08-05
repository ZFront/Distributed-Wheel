package com.wheel.common.util;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;

import java.util.HashMap;
import java.util.Map;

public class BeanUtil {
    /**
     * 两个对象之间的属性复制，把source的值复制给dest
     *
     * @param source
     * @param dest
     * @throws RuntimeException
     */
    public static void copyProperties(Object source, Object dest) {
        try {
            BeanUtils.copyProperties(dest, source);
        } catch (Exception e) {
            throw new RuntimeException("对象属性复制异常", e);
        }
    }


    /**
     * 将一个object对象转换成map,
     * 每一个field的名称为key,值为value
     *
     * @param object 当object为Null时,返回的结果一个size为0的map
     * @return map
     * @throws RuntimeException
     */
    public static Map<String, Object> toMap(Object object) {
        Map<String, Object> map = new HashMap<>();
        try {
            new BeanMap(object).forEach((k, v) -> {
                if (!k.equals("class")) {
                    map.put(k.toString(), v);
                }
            });
        } catch (Exception ex) {
            throw new RuntimeException("bean转换成map失败", ex);
        }
        return map;
    }
}
