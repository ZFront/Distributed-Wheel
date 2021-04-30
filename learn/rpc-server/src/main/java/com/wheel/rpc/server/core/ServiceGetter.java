package com.wheel.rpc.server.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc 服务接收请求，调用
 * @author: zhouf
 */
public class ServiceGetter {
    private static Map<Class, Object> serviceMap = new HashMap<>();

    public static <T> T getServiceByClazz(Class<T> clazz) {
        try {
            if (serviceMap.containsKey(clazz)) {
                return (T) serviceMap.get(clazz);
            } else {
                T bean = clazz.newInstance();
                serviceMap.put(clazz, bean);
                return bean;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
