package com.wheel.timer.entity;

import com.wheel.common.enums.timer.JobTypeEnum;
import com.wheel.common.enums.timer.TimeUnitEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 定时任务
 * @author: zhouf
 * @date: 2020/7/3
 */
@Getter
@Setter
public class JobTask implements Serializable {

    private long id;
    private long version;
    private Date createTime = new Date();
    private Date updateTime;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务类型
     * {@link JobTypeEnum}
     * 1: SimpleTrigger => {@link SimpleTrigger}
     * 2: CronTrigger  =>  {@link CronTrigger}
     */
    private Integer jobType;

    /**
     * 消息目的地 （MQ通知地址）
     */
    private String destination;

    /**
     * 任务开始时间
     */
    private Date startTime;

    /**
     * 任务结束时间
     */
    private Date endTime;
    /**
     * JobType==1时 ： 任务间隔
     */
    private Integer intervals;

    /**
     * JobType==1时 ： 任务间隔的单位
     *
     * {@link TimeUnitEnum}
     */
    private Integer intervalUnit;

    /**
     * 重复次数
     */
    private Integer repeatTimes;

    /**
     * JobType==2时 ： cron表达式
     */
    private String cronExpression;

    /**
     * 任务状态
     * {@link org.quartz.Trigger.TriggerState}
     */
    private String jobStatus;

    /**
     * json格式的参数
     */
    private String paramJson;

    /**
     * 任务描述
     */
    private String jobDesc;

    /**
     * 上次执行时间
     */
    private Date lastExecuteTime;

    /**
     * 下次执行时间
     */
    private Date nextExecuteTime;

    /**
     * 已执行次数
     */
    private Long executedTimes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIntervals() {
        return intervals;
    }

    public void setIntervals(Integer intervals) {
        this.intervals = intervals;
    }

    public Integer getIntervalUnit() {
        return intervalUnit;
    }

    public void setIntervalUnit(Integer intervalUnit) {
        this.intervalUnit = intervalUnit;
    }

    public Integer getRepeatTimes() {
        return repeatTimes;
    }

    public void setRepeatTimes(Integer repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public Date getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(Date lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    public Date getNextExecuteTime() {
        return nextExecuteTime;
    }

    public void setNextExecuteTime(Date nextExecuteTime) {
        this.nextExecuteTime = nextExecuteTime;
    }

    public Long getExecutedTimes() {
        return executedTimes;
    }

    public void setExecutedTimes(Long executedTimes) {
        this.executedTimes = executedTimes;
    }
}
