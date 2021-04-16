package com.wheel.timer.job.core;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * @description 自动注入jobBeanFactory
 * @author: zhouf
 * @date: 2020/7/3
 */
public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory {

    @Override
    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
        return super.createJobInstance(bundle);
    }
}
