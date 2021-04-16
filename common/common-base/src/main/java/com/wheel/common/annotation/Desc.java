package com.wheel.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @description 用于方法、属性作用的描述
 * @author: zhouf
 * @date: 2020/6/1
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Documented
public @interface Desc {
    String value() default "";
}
