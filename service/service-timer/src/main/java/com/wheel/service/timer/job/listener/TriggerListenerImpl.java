package com.wheel.service.timer.job.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.quartz.core.JobRunShell;

/**
 * {@link JobRunShell#run()}
 *
 * @description triggerListener 实现类
 * @author: zhouf
 * @date: 2020/7/17
 */
@Slf4j
public class TriggerListenerImpl implements TriggerListener {

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * job 将要执行时调用
     *
     * @param trigger
     * @param context
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        log.debug("触发器即将触发任务" + trigger.getKey().getName());
    }

    /**
     * 可根据实体业务情况来决定否决job，返回true时表示不执行当前任务，返回false时和它相关的 org.quartz.jobDetail 将被执行。
     *
     * @param trigger
     * @param context
     * @return
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        log.debug("触发器判断是否忽略此次触发" + trigger.getKey().getName());
        return false;
    }

    /**
     * trigger 错过触发时调用
     *
     * @param trigger
     */
    @Override
    public void triggerMisfired(Trigger trigger) {
        log.debug("触发器错过触发" + trigger.getKey().getName());
    }

    /**
     * job 任务执行完毕时触发
     *
     * @param trigger
     * @param context
     * @param triggerInstructionCode
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        log.debug("==>触发器完成任务触发 " + trigger.getKey().getName());
    }
}
