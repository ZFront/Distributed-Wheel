package com.wheel.basis.spring.transaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @desc 写法参考：{@link org.springframework.transaction.annotation.Transactional}
 * @author: zhouf
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyTransaction {

    //指定异常回滚
    Class<? extends Throwable>[] rollbackFor() default {};
}
