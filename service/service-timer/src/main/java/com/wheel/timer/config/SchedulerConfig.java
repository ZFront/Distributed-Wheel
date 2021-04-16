package com.wheel.timer.config;

import com.wheel.timer.job.core.AutowiringSpringBeanJobFactory;
import com.wheel.timer.job.listener.JobListenerImpl;
import com.wheel.timer.job.listener.SchedulerListenerImpl;
import com.wheel.timer.job.listener.TriggerListenerImpl;
import org.quartz.JobListener;
import org.quartz.SchedulerListener;
import org.quartz.TriggerListener;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @description 任务调度配置
 * @author: zhouf
 * @date: 2020/7/3
 */
@SpringBootConfiguration
public class SchedulerConfig {

    /**
     * 数据源
     */
    @Autowired
    DataSource dataSource;

    @Bean
    public JobFactory jobFactory() {
        return new AutowiringSpringBeanJobFactory();
    }

    /**
     * 提供对{@link org.quartz.Scheduler}的创建和配置，管理其生命周期
     * {@link org.quartz.Scheduler} 调度器，所有的任务调度均由他控制
     *
     * @param jobFactory
     * @return
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(quartzProperties());

        factory.setAutoStartup(true);
        //设置全局监听器,也可以单独在scheduler中单独配置
        factory.setGlobalTriggerListeners(triggerListener());
        factory.setGlobalJobListeners(jobListener());
        factory.setSchedulerListeners(schedulerListener());
        return factory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * 定义一个job监听器
     *
     * @return
     */
    @Bean
    public JobListener jobListener() {
        return new JobListenerImpl();
    }

    /**
     * 定义一个trigger监听器
     *
     * @return
     */
    @Bean
    public TriggerListener triggerListener() {
        return new TriggerListenerImpl();
    }

    /**
     * 定义一个scheduler监听器
     *
     * @return
     */
    @Bean
    public SchedulerListener schedulerListener() {
        return new SchedulerListenerImpl();
    }
}
