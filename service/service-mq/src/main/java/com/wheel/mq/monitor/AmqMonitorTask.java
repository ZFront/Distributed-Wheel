package com.wheel.mq.monitor;

import com.wheel.mq.monitor.biz.AmqMonitorBiz;
import com.wheel.mq.prop.AmqMonitorProp;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @desc 根据调研，目前对activemq的监控有两种方式
 * 第一种：使用activemq plugin来实现事件的监控/扩展操作
 * 第二种：通过通过调用activemq Jolokia api来实现监控
 * <p>
 * 在监控过程中是监控broker的异常场景，所以不能依赖MQ来实现
 * 对于一些通知事件的发送，通过spring的ApplicationEventPublisher 事件发布器来进行实现
 * 对于循环执行的任务，则通过ScheduledExecutorService来实现
 * @author: zhouf
 */
public class AmqMonitorTask {

    /**
     * 监控的间隔，默认为60秒
     */
    public int period = 60;

    @Autowired
    private AmqMonitorBiz amqMonitorBiz;

    /**
     * 创建两个循环任务
     * 1： 一个用来监控broker是否健壮
     * 2：一个用来监控消息是否积压
     */
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

    public AmqMonitorTask(AmqMonitorProp monitorProp) {
        // 根据配置，初始化信息
        List<AmqMonitorProp.BrokerNode> brokerNodes = monitorProp.getBrokerNodes();

        // 配置循环任务
        initScheduled(brokerNodes);
    }

    /**
     * 初始化任务配置
     */
    private void initScheduled(List<AmqMonitorProp.BrokerNode> brokerNodes) {
        int size = brokerNodes.size();
        if (size == 0) {
            return;
        }

        // 队列监控
        scheduledExecutorService.scheduleAtFixedRate(()
                -> amqMonitorBiz.doBrokerQueuesMonitor(brokerNodes), 5, period, TimeUnit.SECONDS);

        // TODO 根据自身的业务属性，可以添加其他的监控任务
    }
}
