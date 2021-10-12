package com.wheel.service.timer.job.listener;

import com.wheel.facade.timer.entity.JobTask;
import com.wheel.service.timer.biz.JobTaskBiz;
import com.wheel.service.timer.dao.JobTaskDao;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.core.JobRunShell;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * * {@link JobRunShell#run()}
 *
 * @description
 * @author: zhouf
 * @date: 2020/7/17
 */
@Slf4j
public class JobListenerImpl implements JobListener {

    @Autowired
    private JobTaskBiz jobTaskBiz;

    @Autowired
    private JobTaskDao jobTaskDao;

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * {@link org.quartz.JobDetail} 将要执行时调用这个方法
     *
     * @param context
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {

    }

    /**
     * {@link org.quartz.JobDetail} 即将被执行，但被 {@link org.quartz.TriggerListener} 否觉时调用
     *
     * @param context
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {

    }

    /**
     * {@link org.quartz.JobDetail} 被执行后调用这个方法
     *
     * @param context
     * @param jobException
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String jobName = context.getJobDetail().getKey().getName();
        log.info("任务执行完毕 ==>, {}", jobName);

        JobTask task = jobTaskBiz.getCheckJobTask(jobName);
        try {
            task.setJobStatus(context.getScheduler().getTriggerState(context.getTrigger().getKey()).name());
            task.setLastExecuteTime(context.getScheduledFireTime());
            task.setNextExecuteTime(context.getNextFireTime());
            task.setExecutedTimes(task.getExecutedTimes() + 1);
            jobTaskDao.update(task);
        } catch (Exception e) {
            log.error("定时任务完结，更新任务信息失败, {}", task.getJobName(), e);
        }
    }
}
