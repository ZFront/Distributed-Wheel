package com.wheel.common.util;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassUtil {
    private static ClassLoader classLoader;
    static {
        classLoader = Thread.currentThread().getContextClassLoader();
    }

    /**
     * 通过反射, 获得定义Class时声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
     *
     * @param clazz The class to introspect
     * @param index the Index of the generic declaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be
     * determined
     */
    public static <T> Class<Object> getSuperClassGenericType(final Class<T> clazz, final int index) {
        //返回表示此 Class 所表示的实体（类、接口、基本类型、void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();

        if (! (genType instanceof ParameterizedType)) {
            return Object.class;
        }

        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }

    public static Class getClass(String className) throws ClassNotFoundException {
        //使用 org.apache.commons.lang3.ClassUtils 是因为其支持内部类
        return ClassUtils.getClass(classLoader, className);
    }
}
