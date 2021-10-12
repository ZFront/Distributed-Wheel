package com.wheel.service.timer.job.listener;

import org.quartz.*;
import org.quartz.core.JobRunShell;

/**
 * * {@link JobRunShell#run()}
 *
 * @description
 * @author: zhouf
 * @date: 2020/7/17
 */
public class SchedulerListenerImpl implements SchedulerListener {

    /**
     * 有新的 jobDetail 部署时调用
     *
     * @param trigger
     */
    @Override
    public void jobScheduled(Trigger trigger) {

    }

    /**
     * 有新的jobDetail卸载时调用
     *
     * @param triggerKey
     */
    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {

    }

    /**
     * 当 trigger 再也不会触发时调用这个方法
     *
     * @param trigger
     */
    @Override
    public void triggerFinalized(Trigger trigger) {

    }

    /**
     * trigger 被暂停时调用
     *
     * @param triggerKey
     */
    @Override
    public void triggerPaused(TriggerKey triggerKey) {

    }

    /**
     * triggers 被暂停时调用
     *
     * @param triggerGroup
     */
    @Override
    public void triggersPaused(String triggerGroup) {

    }

    /**
     * 暂停中恢复时调用
     *
     * @param triggerKey
     */
    @Override
    public void triggerResumed(TriggerKey triggerKey) {

    }

    /**
     * 暂停中恢复时调用
     *
     * @param triggerGroup
     */
    @Override
    public void triggersResumed(String triggerGroup) {

    }

    /**
     * 添加时调用
     *
     * @param jobDetail
     */
    @Override
    public void jobAdded(JobDetail jobDetail) {

    }

    /**
     * 删除时调用
     *
     * @param jobKey
     */
    @Override
    public void jobDeleted(JobKey jobKey) {

    }

    /**
     * 暂停时调用
     *
     * @param jobKey
     */
    @Override
    public void jobPaused(JobKey jobKey) {

    }

    /**
     * 暂停时调用
     *
     * @param jobGroup
     */
    @Override
    public void jobsPaused(String jobGroup) {

    }

    /**
     * job 暂停恢复时调用
     *
     * @param jobKey
     */
    @Override
    public void jobResumed(JobKey jobKey) {

    }

    /**
     * jobs 暂停中恢复时调用
     *
     * @param jobGroup
     */
    @Override
    public void jobsResumed(String jobGroup) {

    }

    @Override
    public void schedulerError(String msg, SchedulerException cause) {

    }

    @Override
    public void schedulerInStandbyMode() {

    }

    @Override
    public void schedulerStarted() {

    }

    @Override
    public void schedulerStarting() {

    }

    @Override
    public void schedulerShutdown() {

    }

    @Override
    public void schedulerShuttingdown() {

    }

    @Override
    public void schedulingDataCleared() {

    }
}
