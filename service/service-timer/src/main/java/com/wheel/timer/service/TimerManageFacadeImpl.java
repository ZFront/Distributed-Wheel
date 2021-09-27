package com.wheel.timer.service;

import com.wheel.common.vo.PageQuery;
import com.wheel.common.vo.PageResult;
import com.wheel.timer.biz.JobTaskBiz;
import com.wheel.timer.dao.JobTaskDao;
import com.wheel.timer.entity.JobTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @desc
 * @author: zhouf
 */
@Service
public class TimerManageFacadeImpl implements TimerManageFacade {

    @Autowired
    private JobTaskBiz jobTaskBiz;

    @Autowired
    private JobTaskDao jobTaskDao;

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

    @Override
    public PageResult<List<JobTask>> listPage(Map<String, Object> paramMap, PageQuery pageQuery) {
        return jobTaskDao.listPage(paramMap, pageQuery);
    }
}
