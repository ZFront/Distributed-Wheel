package com.wheel.timer.service;

/**
 * @description 定时管理类
 * @author: zhouf
 * @date: 2020/7/2
 */
public interface TimerManageFacade {

    //添加定时器任务
    boolean addJob();

    //更新任务
    boolean updateJob();

    //删除任务
    boolean deleteJob();

    //暂停任务
    boolean pauseJob();

    //重启任务
    boolean restartJob();

    //触发任务
    boolean triggerJob();
}
