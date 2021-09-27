package com.wheel.timer.service;

import com.wheel.common.vo.PageQuery;
import com.wheel.common.vo.PageResult;
import com.wheel.timer.entity.JobTask;

import java.util.List;
import java.util.Map;

/**
 * @description 定时管理类
 * @author: zhouf
 * @date: 2020/7/2
 */
public interface TimerManageFacade {

    /**
     * 添加定时器任务
     *
     * @return
     */
    void addJob(JobTask task);

    /**
     * 更新任务
     *
     * @return
     */
    void updateJob(JobTask task);

    /**
     * 删除任务
     *
     * @return
     */
    void deleteJob(String jobName);

    /**
     * 暂停任务
     *
     * @return
     */
    boolean pauseJob(String jobName);

    /**
     * 重启任务
     *
     * @return
     */
    boolean restartJob(String jobName);

    /**
     * 触发任务
     *
     * @return
     */
    boolean triggerJob(String jobName);

    /**
     * 发送任务通知
     */
    void sendJobNotify(String jobName);

    /**
     * 列表查询
     *
     * @return
     */
    PageResult<List<JobTask>> listPage(Map<String, Object> paramMap, PageQuery pageQuery);
}
