package com.wheel.timer.timer;

import com.wheel.timer.ServiceTimerApp;
import com.wheel.timer.biz.JobTaskBiz;
import com.wheel.common.constant.P2PDestinations;
import com.wheel.timer.entity.JobTask;
import com.wheel.common.enums.timer.JobTypeEnum;
import com.wheel.common.enums.timer.TimeUnitEnum;
import com.wheel.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @description
 * @author: zhouf
 * @date: 2020/7/15
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceTimerApp.class)
public class JobTaskTest {

    @Autowired
    private JobTaskBiz jobTaskBiz;

    @Test
    public void addSimpleJob() {
        JobTask task = new JobTask();
        task.setJobName("simpleJobTest4");
        task.setJobType(JobTypeEnum.SIMPLE_JOB.getValue());
        task.setDestination(P2PDestinations.QUEUE_TEST);
        task.setStartTime(new Date());
        task.setIntervals(5);
        task.setIntervalUnit(TimeUnitEnum.SECOND.getValue());
        task.setRepeatTimes(5);
        task.setExecutedTimes(0L);
        task.setJobStatus(Trigger.TriggerState.NORMAL.toString());
        task.setParamJson(JsonUtil.toString(task));
        task.setJobDesc("simpleJob测试");
        jobTaskBiz.addJobTask(task);
    }

    @Test
    public void addCronJob() {
        // 每隔1分钟执行一次
        String cronExp = "0 */1 * * * ?";

        JobTask task = new JobTask();
        task.setJobName("cronJob1");
        task.setJobType(JobTypeEnum.CRON_JOB.getValue());
        task.setDestination(P2PDestinations.QUEUE_TEST);
        task.setCronExpression(cronExp);
        task.setStartTime(new Date());
        task.setJobStatus(Trigger.TriggerState.NORMAL.toString());
        task.setParamJson(JsonUtil.toString(task));
        task.setJobDesc("cronJob测试");
        jobTaskBiz.addJobTask(task);
    }

    @Test
    public void deletaJobTask() {
        jobTaskBiz.deleteJobTask("simpleJobTest");
    }

    @Test
    public void pauseJob() {
        if (jobTaskBiz.pauseJob("simpleJobTest3")) {
            log.info("暂停成功");
        } else {
            log.info("暂停失败");
        }
    }

    @Test
    public void triggerJob() {
        if (jobTaskBiz.triggerJob("cronJob1")) {
            log.info("触发成功");
        } else {
            log.info("触发失败");
        }
    }

    @Test
    public void getJobTask() {
        log.info(JsonUtil.toString(jobTaskBiz.getCheckJobTask("simpleJobTest")));
    }

}
