package com.wheel.service.timer.timer;

import com.wheel.service.timer.ServiceTimerApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description
 * @author: zhouf
 * @date: 2020/7/15
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceTimerApp.class)
public class QuarzTest {

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    @Test
    public void quartzTest() throws SchedulerException {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        scheduler.start();

        scheduler.shutdown();
    }
}
