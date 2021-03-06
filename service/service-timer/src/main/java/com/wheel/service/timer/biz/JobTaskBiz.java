package com.wheel.service.timer.biz;

import com.wheel.common.enums.exception.BizCodeEnum;
import com.wheel.common.enums.timer.JobTypeEnum;
import com.wheel.common.exception.BizException;
import com.wheel.common.util.JsonUtil;
import com.wheel.common.util.StringUtil;
import com.wheel.facade.timer.entity.JobTask;
import com.wheel.service.timer.job.core.JobManager;
import com.wheel.service.timer.dao.JobTaskDao;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 一般业务比较多的情况下，可以使用 jobGroup + jobName 来使用
 *
 * @description 定时任务
 * @author: zhouf
 * @date: 2020/7/13
 */
@Slf4j
@Component
public class JobTaskBiz {

    @Autowired
    private JobTaskDao jobTaskDao;

    @Autowired
    private JobManager jobManager;

    @Autowired
    private JobNotifyer jobNotifyer;

    /**
     * 添加任务
     *
     * @param task
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void addJobTask(JobTask task) {
        // 校验参数
        checkTask(task);

        if (task.getExecutedTimes() == null) {
            task.setExecutedTimes(0L);
        }

        try {
            // 判断任务是否存在 ==> jobName
            if (jobManager.checkExists(task.getJobName())) {
                throw new BizException(BizCodeEnum.BIZ_INVALID.getCode(), "定时任务已存在");
            }
            jobTaskDao.insert(task);
            if (!jobManager.addJob(task)) {
                throw new BizException(BizCodeEnum.BIZ_INVALID.getCode(), "添加定时任务失败");
            }
        } catch (Exception e) {
            log.error("添加定时任务发生异常", e);
            throw new BizException(BizCodeEnum.BIZ_ERROR.getCode(), "添加定时任务失败");
        }
    }

    /**
     * 更新定时任务
     *
     * @param jobTask
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateJobTask(JobTask jobTask) {
        checkTask(jobTask);

        JobTask dbJobTask = jobTaskDao.getByJobName(jobTask.getJobName());
        if (dbJobTask == null) {
            throw new BizException(BizCodeEnum.BIZ_ERROR.getCode(), "定时任务不存在");
        }

        // 更新对应的重要参数
        if (JobTypeEnum.SIMPLE_JOB.getValue() == jobTask.getJobType()) {
            dbJobTask.setIntervals(jobTask.getIntervals());
            dbJobTask.setIntervalUnit(jobTask.getIntervalUnit());
        } else if (JobTypeEnum.CRON_JOB.getValue() == jobTask.getJobType()) {
            dbJobTask.setCronExpression(jobTask.getCronExpression());
        }
        dbJobTask.setDestination(jobTask.getDestination());
        dbJobTask.setEndTime(jobTask.getEndTime());
        dbJobTask.setParamJson(jobTask.getParamJson());
        dbJobTask.setJobDesc(jobTask.getJobDesc());

        jobTaskDao.update(dbJobTask);

        try {
            Date result = jobManager.rescheduleJob(dbJobTask);
            if (result == null) {
                // 抛出，让DB更新回滚
                throw new BizException(BizCodeEnum.BIZ_ERROR.getCode(), "更新定时任务失败");
            }
        } catch (Exception e) {
            log.error("更新定时任务失败", e);
            throw new BizException(BizCodeEnum.BIZ_ERROR.getCode(), "更新定时任务失败");
        }
    }


    /**
     * 删除任务
     *
     * @param jobName
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteJobTask(String jobName) {
        JobTask task = getCheckJobTask(jobName);
        jobTaskDao.deleteById(task.getId());

        if (!jobManager.deleteJob(jobName)) {
            // 抛出异常，让异常回滚
            throw new BizException(BizCodeEnum.BIZ_ERROR.getCode(), "删除任务失败");
        }
    }


    /**
     * 暂停任务
     *
     * @param jobName
     * @return
     */
    public boolean pauseJob(String jobName) {
        JobTask task = getCheckJobTask(jobName);
        if (jobManager.pauseJob(jobName)) {
            task.setJobStatus(Trigger.TriggerState.PAUSED.name());
            return jobTaskDao.update(task) > 0;
        } else {
            return false;
        }
    }


    /**
     * 立即触发
     * 需要判断整个实例是否挂起，如果挂起了，则不允许发起
     *
     * @param jobName
     * @return
     */
    public boolean triggerJob(String jobName) {
        getCheckJobTask(jobName);
        try {
            if (jobManager.isInStandbyMode()) {
                log.info("jobName={},已被挂起，无法触发任务", jobName);
                throw new BizException(BizCodeEnum.BIZ_INVALID.getCode(), "任务已被挂起，无法触发该任务");
            }
        } catch (Exception e) {
            log.error("触发定时任务发生异常", e);
        }
        return jobManager.triggerJob(jobName);
    }

    /**
     * 恢复被挂起的定时任务
     *
     * @param jobName
     * @return
     */
    public boolean restartJob(String jobName) {
        getCheckJobTask(jobName);
        try {
            return jobManager.resumeJob(jobName);
        } catch (Exception e) {
            log.error("恢复定时器任务发生异常", e);
            throw new BizException(BizCodeEnum.BIZ_ERROR.getCode(), "重启定时任务失败");
        }
    }

    /**
     * 发送任务通知
     *
     * @param jobName
     */
    public void sendJobNotify(String jobName) {
        JobTask jobTask = getCheckJobTask(jobName);
        log.info("发送任务通知：{}", JsonUtil.toString(jobTask));
        jobNotifyer.sendNotify(jobTask);
    }


    /**
     * 获取任务信息
     * 不存在会直接抛出错误
     *
     * @param jobName
     * @return
     */
    public JobTask getCheckJobTask(String jobName) {
        JobTask task = jobTaskDao.getByJobName(jobName);
        if (task == null) {
            throw new BizException(BizCodeEnum.BIZ_INVALID.getCode(), "该任务不存在");
        }
        return task;
    }

    /**
     * 校验参数是否合理
     *
     * @param task
     */
    private void checkTask(JobTask task) {
        if (task.getJobType() == null) {
            throw new BizException("任务类型(jobType)不能为空");
        }
        if (StringUtil.isEmpty(task.getJobName())) {
            throw new BizException("任务名(jobName)不能为空");
        }
        if (StringUtil.isEmpty(task.getDestination())) {
            throw new BizException("任务通知目的地(destination)不能为空");
        }
        if (task.getStartTime() == null) {
            throw new BizException("开始时间(startTime)不能为空");
        }

        if (JobTypeEnum.SIMPLE_JOB.getValue() == task.getJobType()) {
            if (task.getIntervals() == null) {
                throw new BizException("任务间隔(interval)不能为空");
            }
            if (task.getIntervalUnit() == null) {
                throw new BizException("任务间隔单位(intervalUnit)不能为空");
            }
        } else if (JobTypeEnum.CRON_JOB.getValue() == task.getJobType()) {
            if (StringUtil.isEmpty(task.getCronExpression())) {
                throw new BizException("cron表达式(cronExpression)不能为空");
            }
        } else {
            throw new BizException("未支持的任务类型jobType: " + task.getJobType());
        }
    }
}
