package com.wheel.timer.service;

import com.wheel.timer.biz.JobTaskBiz;
import com.wheel.timer.entity.JobTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @desc
 * @author: zhouf
 */
@Service
public class TimerManageFacadeImpl implements TimerManageFacade {

    @Autowired
    private JobTaskBiz jobTaskBiz;

    @Override
    public void addJob(JobTask task) {
        jobTaskBiz.addJobTask(task);
    }

    @Override
    public void updateJob(JobTask task) {
        jobTaskBiz.updateJobTask(task);
    }

    @Override
    public void deleteJob(String jobName) {
        jobTaskBiz.deleteJobTask(jobName);
    }

    @Override
    public boolean pauseJob(String jobName) {
        return jobTaskBiz.pauseJob(jobName);
    }

    @Override
    public boolean restartJob(String jobName) {
        return jobTaskBiz.restartJob(jobName);
    }

    @Override
    public boolean triggerJob(String jobName) {
        return jobTaskBiz.triggerJob(jobName);
    }

    @Override
    public void sendJobNotify(String jobName) {
        jobTaskBiz.sendJobNotify(jobName);
    }
}
