package com.wheel.learn.basis.spring.transaction;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 * @desc 处理事务的核心
 * @author: zhouf
 */
@Aspect
@Component
public class MyTransactionAopHandler {

    @Autowired
    DbConnectHolder connectHolder;
    Class<? extends Throwable>[] es;

    //拦截所有MyTransaction注解的方法
    @org.aspectj.lang.annotation.Pointcut("@annotation(com.wheel.learn.basis.spring.transaction.MyTransaction)")
    public void Transaction() {

    }

    @Around("Transaction()")
    public Object TransactionProceed(ProceedingJoinPoint proceed) throws Throwable {
        Object result = null;
        Signature signature = proceed.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method == null) {
            return result;
        }
        MyTransaction transaction = method.getAnnotation(MyTransaction.class);
        if (transaction != null) {
            es = transaction.rollbackFor();
        }
        try {
            result = proceed.proceed();
        } catch (Throwable throwable) {
            //异常处理
            completeTransactionAfterThrowing(throwable);
            throw throwable;
        }
        //直接提交
        doCommit();
        return result;
    }

    /**
     * 执行回滚，最后关闭连接和清理线程绑定
     */
    private void doRollBack() {
        try {
            connectHolder.getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectHolder.cleanHolder();
        }
    }

    /**
     * 执行提交，最后关闭连接和清理线程绑定
     */
    private void doCommit() {
        try {
            connectHolder.getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectHolder.cleanHolder();
        }
    }

    /**
     * 异常处理，捕获的异常是目标异常或者其子类，就进行回滚，否则就提交事务。
     */
    private void completeTransactionAfterThrowing(Throwable throwable) {
        if (es != null && es.length > 0) {
            for (Class<? extends Throwable> e : es) {
                if (e.isAssignableFrom(throwable.getClass())) {
                    doRollBack();
                }
            }
        }
        doCommit();
    }
}
