package com.wheel.timer.job.core;

import com.wheel.common.enums.exception.PublicBizCodeEnum;
import com.wheel.common.enums.timer.JobTypeEnum;
import com.wheel.common.enums.timer.TimeUnitEnum;
import com.wheel.common.exception.BizException;
import com.wheel.timer.entity.JobTask;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description 任务管理类
 * @author: zhouf
 * @date: 2020/7/3
 */
@Slf4j
@Component
public class JobManager {

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 暂停整个实例
     *
     * @return
     */
    public boolean pauseScheduler() {
        try {
            schedulerFactoryBean.getScheduler().standby();
            return true;
        } catch (Exception e) {
            log.error("暂停实例失败", e);
            return false;
        }
    }

    /**
     * 实例是否被挂起
     * 由于异常后，无法得知是否真的挂起，顾直接向上抛出异常
     *
     * @return
     */
    public boolean isInStandbyMode() throws SchedulerException {
        return schedulerFactoryBean.getScheduler().isInStandbyMode();
    }

    /**
     * 重启整个实例
     *
     * @return
     */
    public boolean resumeScheduler() {
        try {
            schedulerFactoryBean.getScheduler().start();
            return true;
        } catch (Exception e) {
            log.error("重启实例失败", e);
            return false;
        }
    }

    /**
     * 暂停任务
     *
     * @param jobName
     * @return
     */
    public boolean pauseJob(String jobName) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobName);

        try {
            scheduler.pauseJob(jobKey);
            return true;
        } catch (Exception e) {
            log.error("暂停任务失败", e);
            return false;
        }
    }

    /**
     * 立刻触发任务，仅触发一次
     *
     * @param jobName
     * @return
     */
    public boolean triggerJob(String jobName) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobName);

        try {
            scheduler.triggerJob(jobKey);
            return true;
        } catch (Exception e) {
            log.error("触发任务失败", e);
            return false;
        }
    }

    /**
     * 删除任务
     *
     * @param jobName
     * @return
     */
    public boolean deleteJob(String jobName) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobName);

        try {
            return scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            log.error("触发任务失败", e);
            return false;
        }
    }


    /**
     * 添加任务信息
     *
     * @param task
     * @return
     */
    public boolean addJob(JobTask task) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            JobDetail jobDetail = JobBuilder
                    .newJob(JobExecutor.class)
                    .withIdentity(task.getJobName())
                    .build();

            Trigger trigger = buildTrigger(task);

            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (SchedulerException e) {
            log.error("创建定时器发生异常", e);
            return false;
        }
    }

    /**
     * 更新任务
     *
     * @param task
     */
    public Date rescheduleJob(JobTask task) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        TriggerKey triggerKey = TriggerKey.triggerKey(task.getJobName());

        Trigger trigger = buildTrigger(task);

        // 重新设置Job
        return scheduler.rescheduleJob(triggerKey, trigger);
    }

    /**
     * 恢复任务，返回任务状态
     *
     * @return
     */
    public boolean resumeJob(String jobName) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobName);

        try {
            scheduler.resumeJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            log.error("恢复定时器发生异常", e);
            return false;
        }
    }

    /**
     * 校验是否存在任务
     * 抛错后，无法得知是否真的存在，顾往上抛出异常
     *
     * @param jobName
     * @return
     * @throws SchedulerException 直接向上抛出异常
     */
    public boolean checkExists(String jobName) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        JobKey jobKey = JobKey.jobKey(jobName);
        return scheduler.checkExists(jobKey);
    }

    /**
     * 构建对应的trigger
     *
     * @param task
     * @return
     */
    public Trigger buildTrigger(JobTask task) {
        Trigger trigger;
        TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger();

        // 简单任务
        if (JobTypeEnum.SIMPLE_JOB.getValue() == task.getJobType()) {
            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();

            // 设置时间间隔
            if (TimeUnitEnum.MILL_SECOND.getValue() == task.getIntervalUnit()) {
                scheduleBuilder.withIntervalInMilliseconds(task.getIntervals());
            }
            if (TimeUnitEnum.SECOND.getValue() == task.getIntervalUnit()) {
                scheduleBuilder.withIntervalInSeconds(task.getIntervals());
            }
            if (TimeUnitEnum.MINUTE.getValue() == task.getIntervalUnit()) {
                scheduleBuilder.withIntervalInMinutes(task.getIntervals());
            }
            if (TimeUnitEnum.HOUR.getValue() == task.getIntervalUnit()) {
                scheduleBuilder.withIntervalInHours(task.getIntervals());
            }

            // 设置重复次数
            if (task.getRepeatTimes() != null) {
                // 由于从0开始计数，顾为了跟理解上保持一致，将重复次数减去1
                scheduleBuilder.withRepeatCount(task.getRepeatTimes() - 1);
            }

            triggerBuilder = triggerBuilder.withIdentity(task.getJobName())
                    .withDescription(task.getJobDesc())
                    .withSchedule(scheduleBuilder);

        } else if (JobTypeEnum.CRON_JOB.getValue() == task.getJobType()) {
            // cron任务
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());

            triggerBuilder = triggerBuilder.withIdentity(task.getJobName())
                    .withDescription(task.getJobDesc())
                    .withSchedule(cronScheduleBuilder);
        } else {
            throw new BizException(PublicBizCodeEnum.BIZ_INVALID.getCode(), "未知的任务类型");
        }

        // 设置开始时间、结束时间
        if (task.getStartTime() != null) {
            triggerBuilder.startAt(task.getStartTime());
        }
        if (task.getEndTime() != null) {
            triggerBuilder.endAt(task.getEndTime());
        }

        trigger = triggerBuilder.build();
        return trigger;
    }
}
