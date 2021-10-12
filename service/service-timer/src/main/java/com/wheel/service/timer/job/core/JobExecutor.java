package com.wheel.service.timer.job.core;

import com.wheel.facade.timer.entity.JobTask;
import com.wheel.service.timer.biz.JobNotifyer;
import com.wheel.service.timer.biz.JobTaskBiz;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description job执行类
 * 1、 将类中存储的业务类通过消息系统分发出去，实现业务解耦
 * @author: zhouf
 * @date: 2020/7/3
 */
@Slf4j
public class JobExecutor implements Job {

    @Autowired
    private JobTaskBiz jobTaskBiz;

    @Autowired
    private JobNotifyer jobNotifyer;

    @Override
    public void execute(JobExecutionContext context) {
        JobKey jobKey = context.getJobDetail().getKey();

        JobTask task = jobTaskBiz.getCheckJobTask(jobKey.getName());

        log.info("jobName={}, 发送通知", jobKey.getName());
        jobNotifyer.sendNotify(task);
    }
}
